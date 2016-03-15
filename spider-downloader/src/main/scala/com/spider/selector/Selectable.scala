package com.spider.selector

/**
  * Created by jason on 16-2-2.
  */
trait Selectable {

  def xpath(xpath: String): Selectable

  def $(selector: String): Selectable

  def $(selector: String, attrName: String): Selectable

  def css(selector: String): Selectable

  def css(selector: String, attrName: String): Selectable

  def smartContent: Selectable

  def links: Selectable

  def regex(regex: String): Selectable

  def regex(regex: String, group: Int)

  def regex(regex: String, replacement: String): Selectable

  def toString: String

  def get: String

  def exist: Boolean

  def all: List[String]

  def jsonPath(jsonPath: String): Selectable

  def select(selector: Selector): Selectable

  def selectList(selector: Selector): Selectable

  def nodes: List[Selectable]

}
