#!/bin/bash
set -eo pipefail
aws cloudformation package --template-file template.yaml --s3-bucket sowjanya.com-cftemplates --output-template-file out.yml
aws cloudformation deploy --template-file out.yml --stack-name kafka-producer --capabilities CAPABILITY_NAMED_IAM
