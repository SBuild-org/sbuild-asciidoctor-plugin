package org.sbuild.plugins.asciidoctor

import java.io.File
import de.tototec.sbuild._

/**
 * Configuration for the SBuild Asciidoctor Plugin.
 *
 * Based on it's configuration, this plugin will register various targets.
 * 
 * Most configuration settings are directly submitted to the Asciidoctor generator. 
 * Please refer to the Asciidoctor Manual and the Helf of the Asciidoctor Commandline Interface.
 * 
 * @param sourceFiles The AsciiDoc files.
 * @param targetDir The destination directory for the generated files.
 *   If `[[scala.None]]`, the files will be generated next to their sources.
 * @param backend The backend used to generate the output. 
 *   If `[[scala.None]]`, the default backend of Asciidoctor will be used (probably `html5`).
 * @param baseDir 
 * @param docType
 * @param generateTargetName The Name of the SBuild `[[de.tototec.sbuild.Target Target]]` to invoke the Asciidoctor generator.
 * @param attributes
 * @param compactOutput
 * @param safeMode
 * @param sectionNumbers
 * @param templateDir
 * @param verbose
 */
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

