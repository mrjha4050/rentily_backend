#!/bin/bash

# Set Kafka version and folder
KAFKA_DIR="kafka_2.13-3.7.0"
TOPIC_NAME="chat_messages"

echo "🔄 Stopping any running Kafka/Zookeeper processes..."
# Kill existing Zookeeper/Kafka processes
pkill -f zookeeper.Kafka || true
pkill -f kafka.Kafka || true

sleep 3

echo "✅ Old processes terminated (if any)."

# Start Zookeeper
echo "🚀 Starting Zookeeper..."
osascript -e "tell application \"Terminal\" to do script \"cd $(pwd) && ./$KAFKA_DIR/bin/zookeeper-server-start.sh ./$KAFKA_DIR/config/zookeeper.properties\""

# Wait until Zookeeper is up (port 2181 open)
echo "⏳ Waiting for Zookeeper to be ready..."
until nc -z localhost 2181; do
  sleep 1
done
echo "✅ Zookeeper is up."

# Start Kafka
echo "🚀 Starting Kafka..."
osascript -e "tell application \"Terminal\" to do script \"cd $(pwd) && ./$KAFKA_DIR/bin/kafka-server-start.sh ./$KAFKA_DIR/config/server.properties\""

# Wait until Kafka is up (port 9092 open)
echo "⏳ Waiting for Kafka to be ready..."
until nc -z localhost 9092; do
  sleep 1
done
echo "✅ Kafka is up."

# Wait a bit more to avoid early topic creation failure
sleep 5

# Create Kafka topic
echo "📁 Creating topic '$TOPIC_NAME'..."
./$KAFKA_DIR/bin/kafka-topics.sh --create \
  --topic $TOPIC_NAME \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1

echo "🎉 Setup complete. Topic '$TOPIC_NAME' created successfully."