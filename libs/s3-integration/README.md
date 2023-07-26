# Digiwf S3 Integration

The following steps are needed to run the integration (service and client example) locally.

## Getting Started

1. Build it with `mvn clean install`
2. Run Stack using `docker-compose`
3. Open Minio (http://localhost:9000, user: `minio`, pass: `Test1234`) and create a Bucket.
4. Store the values in `S3_BUCKETNAME`, `S3_ACCESSKEY` und `S3_SECRETKEY`of `stack/local-docker.env`
5. Start `S3IntegrationApplication` (of digiwf-s3-integration-service)
   - Activate Spring Profile `local`
   - Add Environment values from `stack/local-docker.env`

## Testing functionality

1. Start `S3IntegrationClientApplication`
   - Activate Spring profile `streaming`
   - Add Environment values from `stack/local-docker.env`
2. Execute http test located in `/digiwf-integrations/digiwf-s3-integration/digiwf-s3-integration-client-example/src/main/resources/s3-client.http`
3. Inspect the console of the client 



