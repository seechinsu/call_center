package sparks3logger

import org.apache.spark.sql.types._

object schemaByTopic {

  var schemas = collection.mutable.Map.empty[String, StructType]

  val activity_answered = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_activityId", StringType)
      .add("data_activityResponse", StringType)
      .add("data_activityType", StringType)
      .add("data_contextId", StringType)
      .add("data_contextType", StringType)
      .add("data_institutionId", StringType)
      .add("data_responseId", StringType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("activity-answered") = activity_answered

  val activity_created = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_activityContent", StringType)
      .add("data_activityId", StringType)
      .add("data_activitySolution", StringType)
      .add("data_activityType", StringType)
      .add("data_explanationRequired", BooleanType)
      .add("data_institutionId", StringType)
      .add("data_prompt", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("activity-created") = activity_created

  val activity_edited = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_activityContent", StringType)
      .add("data_activityId", StringType)
      .add("data_activityType", StringType)
      .add("data_explanationRequired", BooleanType)
      .add("data_institutionId", StringType)
      .add("data_prompt", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("activity-edited") = activity_edited

  val activity_deleted = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_activityId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("activity-deleted") = activity_deleted

  val activity_published = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_activityId", StringType)
      .add("data_activityType", StringType)
      .add("data_contextId", StringType)
      .add("data_contextType", StringType)
      .add("data_institutionId", StringType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("activity-published") = activity_published

  val activity_unanswered = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_activityId", StringType)
      .add("data_activityResponse", StringType)
      .add("data_activityType", StringType)
      .add("data_contextId", StringType)
      .add("data_contextType", StringType)
      .add("data_institutionId", StringType)
      .add("data_responseId", StringType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("activity-unanswered") = activity_unanswered

  val activity_unpublished = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_activityId", StringType)
      .add("data_activityType", StringType)
      .add("data_contextId", StringType)
      .add("data_contextType", StringType)
      .add("data_institutionId", StringType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("activity-unpublished") = activity_unpublished

  val all_events = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureStatus", StringType)
      .add("data_contentUploadStatus", StringType)
      .add("data_currentDeviceXmlHash", ArrayType(LongType,true))
      .add("data_currentTasksXmlHash", ArrayType(LongType,true))
      .add("data_deviceId", StringType)
      .add("data_deviceType", StringType)
      .add("data_inputSources", ArrayType(StringType,true))
      .add("data_institutionId", StringType)
      .add("data_ipAddress", StringType)
      .add("data_logUploadStatus", StringType)
      .add("data_nextStatusInterval", LongType)
      .add("data_os", StringType)
      .add("data_platform", StringType)
      .add("data_targetDeviceXmlHash", ArrayType(LongType,true))
      .add("data_targetTasksXmlHash", ArrayType(LongType,true))
      .add("data_taskManagerVersion", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_upSince", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("all-events") = all_events

  val assignment_dim_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_allDay", StringType)
      .add("data_allDayDate", StringType)
      .add("data_assignmentGroupId", StringType)
      .add("data_canvasId", StringType)
      .add("data_courseId", StringType)
      .add("data_createdAt", TimestampType)
      .add("data_description", StringType)
      .add("data_dueAt", TimestampType)
      .add("data_gradeGroupStudentsIndividually", StringType)
      .add("data_gradingType", StringType)
      .add("data_id", StringType)
      .add("data_lockAt", TimestampType)
      .add("data_muted", StringType)
      .add("data_possiblePoints", StringType)
      .add("data_submissionTypes", StringType)
      .add("data_timestamp", LongType)
      .add("data_title", StringType)
      .add("data_unlockAt", TimestampType)
      .add("data_updatedAt", TimestampType)
      .add("data_workflowState", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("assignment-dim-event") = assignment_dim_event

  val capture_completed = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-completed") = capture_completed

  val capture_created = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_institutionId", StringType)
      .add("data_name", StringType)
      .add("data_shouldAutoPublish", BooleanType)
      .add("data_shouldStreamLive", BooleanType)
      .add("data_startTime", StringType)
      .add("data_timeZone", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-created") = capture_created

  val capture_deleted = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-deleted") = capture_deleted

  val capture_ended_with_error = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_error", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-ended-with-error") = capture_ended_with_error

  val capture_started = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-started") = capture_started

  val capture_started_notification = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_expectedDuration", LongType)
      .add("data_institutionId", StringType)
      .add("data_instructorId", StringType)
      .add("data_name", StringType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-started-notification") = capture_started_notification

  val capture_state_from_status_post = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_elapsed", LongType)
      .add("data_institutionId", StringType)
      .add("data_state", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-state-from-status-post") = capture_state_from_status_post

  val capture_status_active = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-status-active") = capture_status_active

  val capture_status_waiting = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-status-waiting") = capture_status_waiting

  val capture_stopped_via_device_api = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-stopped-via-device-api") = capture_stopped_via_device_api

  val capture_updated = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_duration", LongType)
      .add("data_institutionId", StringType)
      .add("data_instructorId", StringType)
      .add("data_masterFolderUri", StringType)
      .add("data_name", StringType)
      .add("data_shouldAutoPublish", BooleanType)
      .add("data_shouldStreamLive", BooleanType)
      .add("data_startTime", StringType)
      .add("data_timeZone", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-updated") = capture_updated

  val capture_upload_completed_notification = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_uploadFolderUri", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-upload-completed-notification") = capture_upload_completed_notification

  val capture_upload_started_notification = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_uploadFolderUri", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("capture-upload-started-notification") = capture_upload_started_notification

  val contextual_csv_file_outcome_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_context", StringType)
      .add("data_fileId", StringType)
      .add("data_index", LongType)
      .add("data_institutionId", StringType)
      .add("data_message", StringType)
      .add("data_result", StringType)
      .add("data_sectionId", StringType)
      .add("data_termId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_url", StringType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("contextual-csv-file-outcome-event") = contextual_csv_file_outcome_event

  val contextual_csv_file_uploaded_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_context", StringType)
      .add("data_fileId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_url", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("contextual-csv-file-uploaded-event") = contextual_csv_file_uploaded_event

  val course_created = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_courseId", StringType)
      .add("data_courseIdentifier", StringType)
      .add("data_courseName", StringType)
      .add("data_createdAt", TimestampType)
      .add("data_departmentId", StringType)
      .add("data_institutionId", StringType)
      .add("data_organizationId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_updatedAt", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("course-created") = course_created

  val course_dim_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_accountId", StringType)
      .add("data_canvasId", StringType)
      .add("data_code", StringType)
      .add("data_concludeAt", TimestampType)
      .add("data_createdAt", TimestampType)
      .add("data_enrollmentTermId", StringType)
      .add("data_id", StringType)
      .add("data_name", StringType)
      .add("data_publicVisible", StringType)
      .add("data_rootAccountId", StringType)
      .add("data_sisSourceId", StringType)
      .add("data_startAt", TimestampType)
      .add("data_timestamp", LongType)
      .add("data_typeObject", StringType)
      .add("data_wikiId", StringType)
      .add("data_workflowState", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("course-dim-event") = course_dim_event

  val course_edited = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_courseId", StringType)
      .add("data_courseIdentifier", StringType)
      .add("data_courseName", StringType)
      .add("data_createdAt", TimestampType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_updatedAt", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("course-edited") = course_edited

  val device_audio_status = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_leftAvgLevel", LongType)
      .add("data_leftPeakLevel", LongType)
      .add("data_name", StringType)
      .add("data_rightAvgLevel", LongType)
      .add("data_rightPeakLevel", LongType)
      .add("data_signalPresent", BooleanType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("device-audio-status") = device_audio_status

  val device_client_started = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("device-client-started") = device_client_started

  val device_cpu_percentage_reported = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_cpuPercentage", LongType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("device-cpu-percentage-reported") = device_cpu_percentage_reported

  val device_disk_space_reported = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_deviceId", StringType)
      .add("data_diskId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_totalBytes", LongType)
      .add("data_usedBytes", LongType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("device-disk-space-reported") = device_disk_space_reported

  val device_input_status = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_signalPresent", BooleanType)
      .add("data_sourceName", StringType)
      .add("data_sourceType", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("device-input-status") = device_input_status

  val device_posted_status = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureStatus", StringType)
      .add("data_contentUploadStatus", StringType)
      .add("data_currentDeviceXmlHash", ArrayType(LongType,true))
      .add("data_currentTasksXmlHash", ArrayType(LongType,true))
      .add("data_deviceId", StringType)
      .add("data_deviceType", StringType)
      .add("data_inputSources", ArrayType(StringType,true))
      .add("data_institutionId", StringType)
      .add("data_ipAddress", StringType)
      .add("data_logUploadStatus", StringType)
      .add("data_nextStatusInterval", LongType)
      .add("data_os", StringType)
      .add("data_platform", StringType)
      .add("data_targetDeviceXmlHash", ArrayType(LongType,true))
      .add("data_targetTasksXmlHash", ArrayType(LongType,true))
      .add("data_taskManagerVersion", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_upSince", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("device-posted-status") = device_posted_status

  val device_process_died = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_process", StringType)
      .add("data_status", LongType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("device-process-died") = device_process_died

  val device_software_version_reported = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_version", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("device-software-version-reported") = device_software_version_reported

  val device_temperature_reported = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_deviceId", StringType)
      .add("data_institutionId", StringType)
      .add("data_temperature", DoubleType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("device-temperature-reported") = device_temperature_reported

  val enrollment_fact_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_computedCurrentScore", StringType)
      .add("data_computedFinalScore", StringType)
      .add("data_courseAccountId", StringType)
      .add("data_courseId", StringType)
      .add("data_courseSectionId", StringType)
      .add("data_enrollmentId", StringType)
      .add("data_enrollmentTermId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("enrollment-fact-event") = enrollment_fact_event

  val file_dim_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_baseFileDIMEvent_accountId", StringType)
      .add("data_baseFileDIMEvent_assignmentId", StringType)
      .add("data_baseFileDIMEvent_canvasId", StringType)
      .add("data_baseFileDIMEvent_contentType", StringType)
      .add("data_baseFileDIMEvent_conversationMessageId", StringType)
      .add("data_baseFileDIMEvent_courseId", StringType)
      .add("data_baseFileDIMEvent_displayName", StringType)
      .add("data_baseFileDIMEvent_fileState", StringType)
      .add("data_baseFileDIMEvent_folderId", StringType)
      .add("data_baseFileDIMEvent_groupId", StringType)
      .add("data_baseFileDIMEvent_id", StringType)
      .add("data_baseFileDIMEvent_md5", StringType)
      .add("data_baseFileDIMEvent_ownerEntityType", StringType)
      .add("data_baseFileDIMEvent_quizId", StringType)
      .add("data_baseFileDIMEvent_quizSubmissionId", StringType)
      .add("data_baseFileDIMEvent_replacementFileId", StringType)
      .add("data_baseFileDIMEvent_rootFileId", StringType)
      .add("data_baseFileDIMEvent_submissionId", StringType)
      .add("data_baseFileDIMEvent_uploaderId", StringType)
      .add("data_baseFileDIMEvent_userId", StringType)
      .add("data_couldBeLocked", StringType)
      .add("data_createdAt", TimestampType)
      .add("data_deletedAt", TimestampType)
      .add("data_lockAt", TimestampType)
      .add("data_locked", StringType)
      .add("data_timestamp", LongType)
      .add("data_unlockAt", TimestampType)
      .add("data_updatedAt", TimestampType)
      .add("data_viewedAt", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("file-dim-event") = file_dim_event

  val file_fact_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_baseFileFactEvent_accountId", StringType)
      .add("data_baseFileFactEvent_assignmentGroupId", StringType)
      .add("data_baseFileFactEvent_assignmentId", StringType)
      .add("data_baseFileFactEvent_conversationId", StringType)
      .add("data_baseFileFactEvent_conversationMessageAuthorId", StringType)
      .add("data_baseFileFactEvent_conversationMessageId", StringType)
      .add("data_baseFileFactEvent_courseId", StringType)
      .add("data_baseFileFactEvent_enrollmentRollupId", StringType)
      .add("data_baseFileFactEvent_enrollmentTermId", StringType)
      .add("data_baseFileFactEvent_fileId", StringType)
      .add("data_baseFileFactEvent_folderId", StringType)
      .add("data_baseFileFactEvent_graderId", StringType)
      .add("data_baseFileFactEvent_groupCategoryId", StringType)
      .add("data_baseFileFactEvent_groupId", StringType)
      .add("data_baseFileFactEvent_quizId", StringType)
      .add("data_baseFileFactEvent_quizSubmissionId", StringType)
      .add("data_baseFileFactEvent_replacementFileId", StringType)
      .add("data_baseFileFactEvent_rootFileId", StringType)
      .add("data_baseFileFactEvent_sisSourceId", StringType)
      .add("data_baseFileFactEvent_submissionId", StringType)
      .add("data_size", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_uploaderId", StringType)
      .add("data_userId", StringType)
      .add("data_wikiId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("file-fact-event") = file_fact_event

  val grades_csv_file_uploaded_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_fileId", StringType)
      .add("data_header", StringType)
      .add("data_index", LongType)
      .add("data_institutionId", StringType)
      .add("data_sectionId", StringType)
      .add("data_termId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_url", StringType)
      .add("data_userId", StringType)
      .add("data_value", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("grades-csv-file-uploaded-event") = grades_csv_file_uploaded_event

  val institution_edited = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_country", StringType)
      .add("data_disabled", BooleanType)
      .add("data_imageUrl", StringType)
      .add("data_institutionId", StringType)
      .add("data_name", StringType)
      .add("data_timeZone", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("institution-edited") = institution_edited

  val lesson_attended = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_institutionId", StringType)
      .add("data_lessonId", StringType)
      .add("data_sectionId", StringType)
      .add("data_slideDeckId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("data_videoId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("lesson-attended") = lesson_attended

  val lesson_created = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_institutionId", StringType)
      .add("data_lessonId", StringType)
      .add("data_sectionId", StringType)
      .add("data_slideDeckId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("lesson-created") = lesson_created

  val lesson_deleted = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_institutionId", StringType)
      .add("data_lessonId", StringType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("lesson-deleted") = lesson_deleted

  val lesson_edited = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_institutionId", StringType)
      .add("data_lessonId", StringType)
      .add("data_sectionId", StringType)
      .add("data_slideDeckId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("lesson-edited") = lesson_edited

  val note_created = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_bookmark", BooleanType)
      .add("data_institutionId", StringType)
      .add("data_lessonId", StringType)
      .add("data_noteId", StringType)
      .add("data_noteText", StringType)
      .add("data_notebookId", StringType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("note-created") = note_created

  val note_edited = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_bookmark", BooleanType)
      .add("data_institutionId", StringType)
      .add("data_lessonId", StringType)
      .add("data_noteId", StringType)
      .add("data_noteText", StringType)
      .add("data_notebookId", StringType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("note-edited") = note_edited

  val org_node_created = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_id", StringType)
      .add("data_institutionId", StringType)
      .add("data_name", StringType)
      .add("data_nodeType", StringType)
      .add("data_parentId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("org-node-created") = org_node_created

  val org_node_deleted = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_id", StringType)
      .add("data_institutionId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("org-node-deleted") = org_node_deleted

  val org_node_edited = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_id", StringType)
      .add("data_institutionId", StringType)
      .add("data_name", StringType)
      .add("data_nodeType", StringType)
      .add("data_parentId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("org-node-edited") = org_node_edited

  val presentation_slide_viewed = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_contextId", StringType)
      .add("data_contextType", StringType)
      .add("data_institutionId", StringType)
      .add("data_mediaId", StringType)
      .add("data_numSlides", LongType)
      .add("data_sectionId", StringType)
      .add("data_slideDeckId", StringType)
      .add("data_slideId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_url", StringType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("presentation-slide-viewed") = presentation_slide_viewed

  val question_created = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_body", StringType)
      .add("data_institutionId", StringType)
      .add("data_lessonId", StringType)
      .add("data_mediaId", StringType)
      .add("data_questionId", StringType)
      .add("data_sectionId", StringType)
      .add("data_sectionRole", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("question-created") = question_created

  val question_deleted = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_institutionId", StringType)
      .add("data_lessonId", StringType)
      .add("data_questionId", StringType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("question-deleted") = question_deleted

  val question_edited = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_body", StringType)
      .add("data_imageId", StringType)
      .add("data_institutionId", StringType)
      .add("data_lessonId", StringType)
      .add("data_mediaId", StringType)
      .add("data_questionId", StringType)
      .add("data_sectionId", StringType)
      .add("data_sectionRole", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("question-edited") = question_edited

  val question_response_created = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_body", StringType)
      .add("data_institutionId", StringType)
      .add("data_lessonId", StringType)
      .add("data_mediaId", StringType)
      .add("data_questionId", StringType)
      .add("data_responseId", StringType)
      .add("data_sectionId", StringType)
      .add("data_sectionRole", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("question-response-created") = question_response_created

  val question_response_deleted = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_institutionId", StringType)
      .add("data_lessonId", StringType)
      .add("data_questionId", StringType)
      .add("data_responseId", StringType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("question-response-deleted") = question_response_deleted

  val request_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_assignmentId", StringType)
      .add("data_conversationId", StringType)
      .add("data_courseAccountId", StringType)
      .add("data_courseId", StringType)
      .add("data_discussionId", StringType)
      .add("data_httpMethod", StringType)
      .add("data_httpStatus", StringType)
      .add("data_id", StringType)
      .add("data_interactionMicros", StringType)
      .add("data_quizId", StringType)
      .add("data_realUserId", StringType)
      .add("data_remoteIp", StringType)
      .add("data_requestTimeUtc", StringType)
      .add("data_rootAccountId", StringType)
      .add("data_sessionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_url", StringType)
      .add("data_userId", StringType)
      .add("data_webApplicationAction", StringType)
      .add("data_webApplicationContextId", StringType)
      .add("data_webApplicationContextType", StringType)
      .add("data_webApplicationController", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("request-event") = request_event

  val search_update_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_docId", StringType)
      .add("data_domain", StringType)
      .add("data_updateTime", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("search-update-event") = search_update_event

  val search_update_needed_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_docId", StringType)
      .add("data_domain", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("search-update-needed-event") = search_update_needed_event

  val section_created = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_courseId", StringType)
      .add("data_institutionId", StringType)
      .add("data_instructorId", StringType)
      .add("data_scheduleIds", ArrayType(StringType,true))
      .add("data_sectionId", StringType)
      .add("data_sectionNumber", StringType)
      .add("data_termId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("section-created") = section_created

  val section_deleted = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("section-deleted") = section_deleted

  val section_edited = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_courseId", StringType)
      .add("data_description", StringType)
      .add("data_institutionId", StringType)
      .add("data_instructorId", StringType)
      .add("data_scheduleIds", ArrayType(StringType,true))
      .add("data_sectionId", StringType)
      .add("data_sectionNumber", StringType)
      .add("data_termId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("section-edited") = section_edited

  val stored_procedure_call_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_name", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("stored-procedure-call-event") = stored_procedure_call_event

  val term_created = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_institutionId", StringType)
      .add("data_name", StringType)
      .add("data_termId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("term-created") = term_created

  val term_deleted = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_termId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("term-deleted") = term_deleted

  val user_created = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_createdAt", TimestampType)
      .add("data_email", StringType)
      .add("data_firstName", StringType)
      .add("data_institutionId", StringType)
      .add("data_lastName", StringType)
      .add("data_roles", ArrayType(StringType,true))
      .add("data_status", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_updatedAt", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("user-created") = user_created

  val user_dim_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_birthdate", StringType)
      .add("data_canvasId", StringType)
      .add("data_countryCode", StringType)
      .add("data_createdAt", TimestampType)
      .add("data_gender", StringType)
      .add("data_id", StringType)
      .add("data_locale", StringType)
      .add("data_name", StringType)
      .add("data_public", StringType)
      .add("data_rootAccountId", StringType)
      .add("data_schoolName", StringType)
      .add("data_schoolPosition", StringType)
      .add("data_sortableName", StringType)
      .add("data_timeZone", StringType)
      .add("data_timestamp", LongType)
      .add("data_visibility", StringType)
      .add("data_workflowState", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("user-dim-event") = user_dim_event

  val user_edited = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_createdAt", TimestampType)
      .add("data_email", StringType)
      .add("data_firstName", StringType)
      .add("data_institutionId", StringType)
      .add("data_lastName", StringType)
      .add("data_roles", ArrayType(StringType,true))
      .add("data_status", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_updatedAt", TimestampType)
      .add("data_userId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("user-edited") = user_edited

  val video_presentation_processing_queued = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_videoPresentationId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("video-presentation-processing-queued") = video_presentation_processing_queued

  val video_presentation_ready_to_view = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_captureId", StringType)
      .add("data_institutionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_videoPresentationId", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("video-presentation-ready-to-view") = video_presentation_ready_to_view

  val video_scene_viewed = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_contextId", StringType)
      .add("data_contextType", StringType)
      .add("data_institutionId", StringType)
      .add("data_mediaId", StringType)
      .add("data_sceneDuration", LongType)
      .add("data_sceneStartTime", LongType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_url", StringType)
      .add("data_userId", StringType)
      .add("data_videoDuration", LongType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("video-scene-viewed") = video_scene_viewed

  val wiki_page_dim_event = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_body", StringType)
      .add("data_canvasId", StringType)
      .add("data_couldBeLocked", StringType)
      .add("data_createdAt", TimestampType)
      .add("data_editingRoles", StringType)
      .add("data_id", StringType)
      .add("data_protectedEditing", StringType)
      .add("data_revisedAt", TimestampType)
      .add("data_timestamp", TimestampType)
      .add("data_title", StringType)
      .add("data_updatedAt", TimestampType)
      .add("data_url", StringType)
      .add("data_workflowState", StringType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("wiki-page-dim-event") = wiki_page_dim_event

    val wiki_page_fact_event = {
    (new StructType)
    .add("aggregateId", StringType)
    .add("aggregateType", StringType)
    .add("createdAt", TimestampType)
    .add("data_accountId", StringType)
    .add("data_enrollmentTermId", StringType)
    .add("data_groupCategoryId", StringType)
    .add("data_parentCourseAccountId", StringType)
    .add("data_parentCourseId", StringType)
    .add("data_parentGroupAccountId", StringType)
    .add("data_parentGroupId", StringType)
    .add("data_rootAccountId", StringType)
    .add("data_timestamp", TimestampType)
    .add("data_userId", StringType)
    .add("data_viewCount", StringType)
    .add("data_wikiId", StringType)
    .add("data_wikiPageCommentsCount", StringType)
    .add("data_wikiPageId", StringType)
    .add("eventType", StringType)
    .add("id", StringType)
    .add("version", LongType)
  }
  schemas("wiki-page-fact-event") = wiki_page_fact_event

  val session_renewed = {
    (new StructType)
      .add("session_applicationId", StringType)
      .add("session_authenticatedAt", TimestampType)
      .add("session_domain", StringType)
      .add("session_id", StringType)
      .add("session_institutionId", StringType)
      .add("session_jwtId", StringType)
      .add("session_sessionTimeout", LongType)
      .add("session_ssoId", StringType)
      .add("session_startedAt", TimestampType)
      .add("session_tokenTimeout", LongType)
      .add("session_userId", StringType)
      .add("status", StringType)
      .add("timestamp", StringType)
  }
  schemas("session-renewed") = session_renewed

  val session_created = {
    (new StructType)
      .add("session_applicationId", StringType)
      .add("session_domain", StringType)
      .add("session_id", StringType)
      .add("session_institutionId", StringType)
      .add("session_sessionTimeout", LongType)
      .add("session_ssoId", StringType)
      .add("session_startedAt", TimestampType)
      .add("session_tokenTimeout", LongType)
      .add("session_userId", StringType)
      .add("status", StringType)
      .add("timestamp", StringType)
  }
  schemas("session-created") = session_created

  val session_authenticated = {
    (new StructType)
      .add("session_applicationId", StringType)
      .add("session_authenticatedAt", TimestampType)
      .add("session_domain", StringType)
      .add("session_id", StringType)
      .add("session_institutionId", StringType)
      .add("session_jwtId", StringType)
      .add("session_sessionTimeout", LongType)
      .add("session_ssoId", StringType)
      .add("session_startedAt", TimestampType)
      .add("session_tokenTimeout", LongType)
      .add("session_userId", StringType)
      .add("status", StringType)
      .add("timestamp", StringType)
  }
  schemas("session-authenticated") = session_authenticated

  val course_deleted = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_courseId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("course-deleted") = course_deleted

  val term_edited = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_institutionId", StringType)
      .add("data_name", StringType)
      .add("data_termId", StringType)
      .add("data_timestamp", TimestampType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("term-edited") = term_edited

  val load_test_one = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_contextId", StringType)
      .add("data_contextType", StringType)
      .add("data_institutionId", StringType)
      .add("data_mediaId", StringType)
      .add("data_sceneDuration", LongType)
      .add("data_sceneStartTime", LongType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_url", StringType)
      .add("data_userId", StringType)
      .add("data_videoDuration", LongType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("load-test-one") = load_test_one

  val load_test_two = {
    (new StructType)
      .add("aggregateId", StringType)
      .add("aggregateType", StringType)
      .add("createdAt", TimestampType)
      .add("data_contextId", StringType)
      .add("data_contextType", StringType)
      .add("data_institutionId", StringType)
      .add("data_mediaId", StringType)
      .add("data_sceneDuration", LongType)
      .add("data_sceneStartTime", LongType)
      .add("data_sectionId", StringType)
      .add("data_timestamp", TimestampType)
      .add("data_url", StringType)
      .add("data_userId", StringType)
      .add("data_videoDuration", LongType)
      .add("eventType", StringType)
      .add("id", StringType)
      .add("version", LongType)
  }
  schemas("load-test-two") = load_test_two
}
