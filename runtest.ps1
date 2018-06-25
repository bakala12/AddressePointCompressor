Param (
	[Parameter(Mandatory=$true)]
	$InputDir,
	[Parameter(Mandatory=$true)]
	$BenchmarkName,
	[Parameter(Mandatory=$true)]
	$OutputDir,
	[Parameter(Mandatory=$true)]
	$DataDir,
	[Parameter(Mandatory=$true)]
	$PlotDir,
	$Iterations = 2000
)

$pathToJar = "./target/AddressPointCompression-1.0-jar-with-dependencies.jar"

$inputFile = "$InputDir\$BenchmarkName.vrp"
$outputFile = "$OutputDir\$BenchmarkName\$($BenchmarkName)Normal.sol"
$outputFileCompression = "$OutputDir\$BenchmarkName\$($BenchmarkName)Compression.sol"
$dataFile = "$DataDir\$BenchmarkName\$($BenchmarkName)Normal.csv"
$dataFileCompression = "$DataDir\$BenchmarkName\$($BenchmarkName)Compression.csv"
$plotDirNormal = "$PlotDir\$BenchmarkName\$($BenchmarkName)Normal"
$plotDirCompression = "$PlotDir\$BenchmarkName\$($BenchmarkName)Compression"
$resultFileNameNormal = "$OutputDir\$BenchmarkName\$($BenchmarkName)Full.result"
$resultFileNameCompression = "$OutputDir\$BenchmarkName\$($BenchmarkName)Compressed.result"
$solutionRouteFileFull = "$OutputDir\$BenchmarkName\$($BenchmarkName)Full.route"
$solutionRouteFileCompressed = "$OutputDir\$BenchmarkName\$($BenchmarkName)Compressed.route"

New-Item -Force -Name "$OutputDir" -ItemType Directory | Out-Null
New-Item -Force -Name "$OutputDir\$BenchmarkName" -ItemType Directory | Out-Null
New-Item -Force -Name "$DataDir" -ItemType Directory | Out-Null
New-Item -Force -Name "$DataDir\$BenchmarkName" -ItemType Directory | Out-Null
New-Item -Force -Name "$PlotDir" -ItemType Directory | Out-Null
New-Item -Force -Name "$PlotDir\$BenchmarkName" -ItemType Directory | Out-Null
New-Item -Force -Name "$plotDirNormal" -ItemType Directory | Out-Null
New-Item -Force -Name "$plotDirCompression" -ItemType Directory | Out-Null

$currDir = Split-Path -Path $MyInvocation.MyCommand.Path

Write-Output "Start test for $($BenchmarkName)"
Start-Process java -ArgumentList "-jar $pathToJar -i $currDir\$inputFile -o $currDir\$outputFile -d $currDir\$dataFile -p $currDir\$plotDirNormal -iter $Iterations -r $currDir\$resultFileNameNormal -s $currDir\$solutionRouteFileFull" -Wait -NoNewWindow
Start-Process java -ArgumentList "-jar $pathToJar -i $currDir\$inputFile -o $currDir\$outputFileCompression -d $currDir\$dataFileCompression -p $currDir\$plotDirCompression -iter $Iterations -c -r $currDir\$resultFileNameCompression -s $currDir\$solutionRouteFileCompressed" -Wait -NoNewWindow
Write-Output "End test for $($BenchmarkName)"