Param (
	$InputDir,
	$BenchmarkName,
	$OutputDir,
	$DataDir,
	$PlotDir
)

$pathToJar = "./target/AddressPointCompression-1.0-jar-with-dependencies.jar"

$inputFile = "$InputDir\$BenchmarkName.vrp"
$outputFile = "$OutputDir\$BenchmarkName\$($BenchmarkName)Normal.sol"
$outputFileCompression = "$OutputDir\$BenchmarkName\$($BenchmarkName)Compression.sol"
$dataFile = "$DataDir\$BenchmarkName\$($BenchmarkName)Normal.csv"
$dataFileCompression = "$DataDir\$BenchmarkName\$($BenchmarkName)Compression.csv"
$plotDirNormal = "$PlotDir\$BenchmarkName\$($BenchmarkName)Normal"
$plotDirCompression = "$PlotDir\$BenchmarkName\$($BenchmarkName)Compression"

New-Item -Force -Name "$OutputDir" -ItemType Directory
New-Item -Force -Name "$OutputDir\$BenchmarkName" -ItemType Directory
New-Item -Force -Name "$DataDir" -ItemType Directory
New-Item -Force -Name "$DataDir\$BenchmarkName" -ItemType Directory
New-Item -Force -Name "$PlotDir" -ItemType Directory
New-Item -Force -Name "$PlotDir\$BenchmarkName" -ItemType Directory
New-Item -Force -Name "$plotDirNormal" -ItemType Directory
New-Item -Force -Name "$plotDirCompression" -ItemType Directory

$currDir = Split-Path -Path $MyInvocation.MyCommand.Path

Write-Output "Start test for $($BenchmarkName)"
Start-Process java -ArgumentList "-jar $pathToJar -i $currDir\$inputFile -o $currDir\$outputFile -d $currDir\$dataFile -p $currDir\$plotDirNormal" -Wait -NoNewWindow
Start-Process java -ArgumentList "-jar $pathToJar -i $currDir\$inputFile -o $currDir\$outputFileCompression -d $currDir\$dataFileCompression -p $currDir\$plotDirCompression -c" -Wait -NoNewWindow
Write-Output "End test for $($BenchmarkName)"




