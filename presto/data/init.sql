create database if not exists lib_events;

create EXTERNAL table lib_events.audit_access(
`meta` struct<`timestamp`:timestamp,`request_id`:string,`tenant_id`:string,`user_id`:string>,
`request` struct<`method`:string,`path`:string,`query`:string,`referrer`:string,`user_agent`:string>,
`requestor` struct<`user`:string,`role`:string>,
`response` struct<`status`:int,`time_ms`:bigint>,
`ids` struct<`user`:string>,
`bulk_ids` struct<`user`:array<string>>,
dt STRING
)
ROW FORMAT SERDE
'org.apache.hive.hcatalog.data.JsonSerDe'
STORED AS TEXTFILE
LOCATION
 '/user/hive/warehouse/audit-access';

