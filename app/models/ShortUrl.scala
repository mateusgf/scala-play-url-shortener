package models

import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.ws._
import play.api.Play.current

import play.api.libs.json._

case class ShortUrl(hash: String, fullUrl: String)

object ShortUrl {
  implicit  val fmt = Json.format[ShortUrl]

  def lookup(hash: String)(implicit ec: ExecutionContext): Future[Option[ShortUrl]] = {
    ShortUrlDAO.findByHash(hash) map { oFullUrl =>
      oFullUrl map { fullUrl =>
        ShortUrl(hash, fullUrl)
      }
    }
  }


  // Command CURL:
  // curl localhost:9000/urls -X POST --data '{"fullurl" : "http://google.com"}' -H 'Content-type: application/json'
  def shorten(fullUrl: String)(implicit ec: ExecutionContext): Future[ShortUrl] = {
    nextUniqueId map { nextId =>
      hashId(nextId)
    } flatMap { hash =>
      ShortUrlDAO.saveMapping(hash, fullUrl) map { _ =>
        ShortUrl(hash, fullUrl)
      }
    }

  }


  val countioServiceUrl = "http://count.io/vb/urlshortener/hash+"
  val countioRequestHolder = WS.url(countioServiceUrl)


  private def nextUniqueId(implicit ec: ExecutionContext): Future[Int] = {
    countioRequestHolder.post("") map { response =>
      (response.json \ "count").as[Int]
    }
  }

  private val hashIndex = "abcdefgABCDEF12345"
  private def hashId(id: Int, acc: String = ""): String =
    if (id == 0)
      acc
    else
      hashId(id / hashIndex.length, acc + hashIndex.charAt(id % hashIndex.length))

}