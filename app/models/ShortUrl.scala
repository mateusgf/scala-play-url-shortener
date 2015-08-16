package models

import scala.concurrent.{ExecutionContext, Future}

import play.api.libs.json._

case class ShortUrl(hash: String, fullUrl: String)

object ShortUrl {
  implicit  val fmt = Json.format[ShortUrl]

  def lookup(hash: String)(implicit ec: ExecutionContext): Future[Option[ShortUrl]] = Future {
    // TODO: implement all the things
    Some(ShortUrl(hash, "https://google.com"))
  }

  def shorten(fullUrl: String)(implicit ec: ExecutionContext): Future[ShortUrl] = Future {
    // TODO: Implement

    ShortUrl("testingurl", fullUrl);
  }
}