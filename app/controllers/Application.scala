package controllers

import models.ShortUrl
import play.api._
import play.api.mvc._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class Application extends Controller {

  def redirect(hash: String) = Action.async {
    ShortUrl.lookup(hash) map {
      case None => NotFound("That URL can' be found.")
      case Some(shortUrl) => Redirect(shortUrl.fullUrl, 301)
    }
  }

  def shorten =  Action.async {
    Future.successful(Ok("Doning it yet"))
  }

}
