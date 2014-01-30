package org.sbuild.plugins.asciidoctor

import java.io.File
import de.tototec.sbuild._

case class Asciidoctor(
  sourceFiles: TargetRefs = Seq(),
  targetDir: Option[File] = None,
  backend: Option[String] = None,
  baseDir: Option[File] = None,
  docType: Option[String] = None,
  generateTargetName: String,
  attributes: Map[String, String] = Map(),
  compactOutput: Boolean = false,
  safeMode: Option[String] = None,
  sectionNumbers: Boolean = false,
  templateDir: Option[File] = None,
  verbose: Boolean = false)

