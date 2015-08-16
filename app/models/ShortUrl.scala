package models

import scala.concurrent.{ExecutionContext, Future}

import play.api.libs.json._

case class ShortUrl(hash: String, fullUrl: String)

object ShortUrl {
  implicit  val fmt = Json.format[ShortUrl]

  def lookup(hash: String)(implicit ec: ExecutionContext): Future[Option[ShortUrl]] = Future {
    // TODO: Read mapping from the database
    Some(ShortUrl(hash, "https://google.com"))
  }

  def shorten(fullUrl: String)(implicit ec: ExecutionContext): Future[ShortUrl] = {
    // TODO: Compute unique hash
    var nextId = nextUniqueId
    var hash = hashId(nextId)

    ShortUrlDAO.saveMapping(hash, fullUrl) map { _ =>
      ShortUrl(hash, fullUrl)
    }
  }


  private var nextId = 1
  private def nextUniqueId: Int = {
    // TODO: Resolve race condition AND persist between app runs
    val id = nextId
    nextId += 1
    id
  }

  private val hashIndex = "abcdefgABCDEF12345"
  private def hashId(id: Int, acc: String = ""): String =
    if (id == 0)
      acc
    else
      hashId(id / hashIndex.length, acc + hashIndex.charAt(id % hashIndex.length))

}