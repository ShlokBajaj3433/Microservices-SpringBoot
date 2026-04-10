#!/usr/bin/env bash

set -e

kind create cluster --name microservices-kind --config kind-config.yaml

echo "Loading Docker Images into Kind Cluster"

if [ -f ./kind-load.sh ]; then
	chmod +x ./kind-load.sh
	./kind-load.sh
else
	echo "kind-load.sh not found, skipping image loading"
fi

echo "===Kind Cluster Started==="