# Simple PowerShell test for login endpoint
param(
    [string]$Email = "rohan@gmail.com",
    [string]$Password = "rohan1234"
)

$body = @{
    email = $Email
    password = $Password
} | ConvertTo-Json

Write-Host "Testing login endpoint..." -ForegroundColor Cyan
Write-Host "Request Body: $body" -ForegroundColor Gray

try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/auth/login" `
        -Method POST `
        -ContentType "application/json" `
        -Body $body `
        -UseBasicParsing
    
    Write-Host "Status Code: $($response.StatusCode)" -ForegroundColor Green
    Write-Host "Response: $($response.Content)" -ForegroundColor Green
    
    $jsonResponse = $response.Content | ConvertFrom-Json
    if ($jsonResponse.token) {
        Write-Host "Token received: $($jsonResponse.token.Substring(0, [Math]::Min(50, $jsonResponse.token.Length)))..." -ForegroundColor Cyan
        return $jsonResponse.token
    }
} catch {
    Write-Host "Error Status: $($_.Exception.Response.StatusCode.value__)" -ForegroundColor Red
    Write-Host "Error Message: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Response Body: $responseBody" -ForegroundColor Red
    }
}
