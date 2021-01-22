# kotlin-spring-example

This is a simple Spring project in Kotlin. The application will send and retrieve files to a MinIO instance.
You can test this application by starting an instance of MinIO on docker with the command:
```
docker run -p 9000:9000 \
  -e "MINIO_ROOT_USER=AKIAIOSFODNN7EXAMPLE" \
  -e "MINIO_ROOT_PASSWORD=wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY" \
  minio/minio server /data
```
To STORE a file in MinIO, you can use Postman.
Request method: POST
URL: localhost/8080

In the 'Body' tab, select 'form-data'. You will find a table with KEY and VALUE.
in KEY: type "file" on the left side and then select "File" on the dropdown on the right side.
in VALUE: you can select the file to import.

To RETRIEVE a previously stored file, you can simply use your browser to go to localhost/8080/<filename>

Enoy!
