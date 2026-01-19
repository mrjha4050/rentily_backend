#!/bin/bash

# Test script for /api/auth/me endpoint
# Usage: ./test-api.sh <email> <password>

EMAIL=$1
PASSWORD=$2
BASE_URL="http://localhost:8080"

if [ -z "$EMAIL" ] || [ -z "$PASSWORD" ]; then
    echo "Usage: ./test-api.sh <email> <password>"
    exit 1
fi

echo "Step 1: Logging in..."
LOGIN_RESPONSE=$(curl -s -X 'POST' \
  "$BASE_URL/api/auth/login" \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d "{
  \"email\": \"$EMAIL\",
  \"password\": \"$PASSWORD\"
}")

echo "Login Response: $LOGIN_RESPONSE"

# Extract token (simple extraction, assumes JSON response with "token" field)
TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*' | cut -d'"' -f4)

if [ -z "$TOKEN" ]; then
    echo "Error: Failed to get token. Please check your credentials."
    exit 1
fi

echo ""
echo "Step 2: Getting current user info..."
curl -X 'GET' \
  "$BASE_URL/api/auth/me" \
  -H 'accept: */*' \
  -H "Authorization: Bearer $TOKEN"

echo ""
