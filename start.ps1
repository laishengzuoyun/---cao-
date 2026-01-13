# 启动Spring Boot项目的PowerShell脚本
# 自动添加Maven到路径
$mavenPath = "D:\开始的开始\apache-maven-3.8.8\bin"
if (-not $env:Path.Contains($mavenPath)) {
    $env:Path += ";$mavenPath"
}

Write-Host "Maven路径已设置"
mvn -v

Write-Host "正在启动Spring Boot项目..."
mvn spring-boot:run
