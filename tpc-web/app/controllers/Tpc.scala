package controllers

import play.api.mvc._


/**
 * Created by ilan.s on 11/5/14.
 */
object Tpc extends Controller {

  def displayForm = Action {
    Ok(views.html.tpc("hello"))
  }

}
