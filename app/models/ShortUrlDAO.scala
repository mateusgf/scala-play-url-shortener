package models

import play.api.db.DB

import scala.concurrent.{Future, ExecutionContext}

import play.api.Play.current
import anorm._

object ShortUrlDAO {
  def saveMapping(hash: String, fullUrl: String)(implicit ec: ExecutionContext): Future[Unit] = Future {
    DB.withConnection { implicit c =>
      var sql = SQL("INSERT INTO shorturls (short, fullurl) VALUE({short}, {fullurl})")
        .on("short" -> hash, "fullurl" -> fullUrl)

      sql.executeInsert()
    }
  }

  def findByHash(hash: String)(implicit ec: ExecutionContext): Future[Option[String]] = Future {
    DB.withConnection { implicit c =>
      var sql = SQL("SELECT fullurl FROM shorturls WHERE short = {short} LIMIT 1")
        .on("short" -> hash)

      sql().headOption map { row =>
        row[String]("fullurl")
      }
    }
  }
}
