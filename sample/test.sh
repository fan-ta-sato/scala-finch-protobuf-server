#!/bin/sh

PROTOC=/usr/local/bin/protoc

SRC_ROOT=$HOME/Git/GitHub/fan-ta-sato/scala-finch-protobuf-server
PROTO_DIR=$SRC_ROOT/src/main/protobuf

REQ=$SRC_ROOT/sample/hello.proto.txt
SERVER=localhost:50051


cat $REQ | \
    protoc --encode=com.example.protos.HelloRequest -I$SRC_ROOT/src/main/protobuf hello.proto | \
    curl -s -X POST -H 'Content-Type: application/octet-stream' --data-binary @- http://$SERVER/pbhello | \
    protoc --decode=com.example.protos.HelloReply -I$SRC_ROOT/src/main/protobuf hello.proto | \
    xargs -0 echo -e

