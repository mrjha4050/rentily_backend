# PowerShell script for testing /api/auth/me endpoint
# Usage: .\test-api.ps1 -Email "user@example.com" -Password "password"

param(
    [Parameter(Mandatory=$true)]
    [string]$Email,
    
    [Parameter(Mandatory=$true)]
    [string]$Password
)

$BaseUrl = "http://localhost:8080"

Write-Host "Step 1: Logging in..." -ForegroundColor Cyan

try {
    # Create request body as hashtable and convert to JSON
    $body = @{
        email = $Email
        password = $Password
    } | ConvertTo-Json

    $loginResponse = Invoke-RestMethod -Uri "$BaseUrl/api/auth/login" `
        -Method POST `
        -ContentType "application/json" `
        -Body $body
    
    $token = $loginResponse.token
    
    if ([string]::IsNullOrEmpty($token)) {
        Write-Host "Error: Failed to get token. Please check your credentials." -ForegroundColor Red
        exit 1
    }
    
    Write-Host "Login successful!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Step 2: Getting current user info..." -ForegroundColor Cyan
    
    $headers = @{
        "Authorization" = "Bearer $token"
        "accept" = "*/*"
    }
    
    Write-Host ""
    Write-Host "Headers being sent:" -ForegroundColor Yellow
    Write-Host "  Authorization: Bearer $($token.Substring(0, [Math]::Min(50, $token.Length)))..." -ForegroundColor Gray
    Write-Host "  accept: $($headers['accept'])" -ForegroundColor Gray
    Write-Host ""
    
    $meResponse = Invoke-RestMethod -Uri "$BaseUrl/api/auth/me" `
        -Method GET `
        -Headers $headers
    
    Write-Host ""
    Write-Host "Current User Info:" -ForegroundColor Green
    $meResponse | ConvertTo-Json -Depth 10
    
} catch {
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.ErrorDetails.Message) {
        Write-Host $_.ErrorDetails.Message -ForegroundColor Red
    }
}
