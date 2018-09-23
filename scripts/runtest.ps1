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
	[Parameter(Mandatory=$true)]
	$NumberOfRuns,
	$Iterations = 2000
)

$pathToJar = "../target/AddressPointCompressor-1.0-jar-with-dependencies.jar"
$currDir = Split-Path -Path $MyInvocation.MyCommand.Path
$currDir = Split-Path -Parent $currDir

$inputFile = "$InputDir\$BenchmarkName.vrp"
$outputFile = "$OutputDir\$BenchmarkName\$($BenchmarkName)Full.sol"
$outputFileCompression = "$OutputDir\$BenchmarkName\$($BenchmarkName)Compressed.sol"
$dataFile = "$DataDir\$BenchmarkName\$($BenchmarkName)Full.csv"
$dataFileCompression = "$DataDir\$BenchmarkName\$($BenchmarkName)Compression.csv"
$plotDirNormal = "$PlotDir\$BenchmarkName\$($BenchmarkName)Full"
$plotDirCompression = "$PlotDir\$BenchmarkName\$($BenchmarkName)Compression"
$resultFileNameNormal = "$OutputDir\$BenchmarkName\$($BenchmarkName)Full.result"
$resultFileNameCompression = "$OutputDir\$BenchmarkName\$($BenchmarkName)Compressed.result"
$solutionRouteFileFull = "$OutputDir\$BenchmarkName\$($BenchmarkName)Full.route"
$solutionRouteFileCompressed = "$OutputDir\$BenchmarkName\$($BenchmarkName)Compressed.route"
$generalInfoFileFull = "$OutputDir\$BenchmarkName\$($BenchmarkName)Full.info"
$generalInfoFileCompressed = "$OutputDir\$BenchmarkName\$($BenchmarkName)Compressed.info"

New-Item -Force -Name "$OutputDir" -ItemType Directory | Out-Null
New-Item -Force -Name "$OutputDir\$BenchmarkName" -ItemType Directory | Out-Null
New-Item -Force -Name "$DataDir" -ItemType Directory | Out-Null
New-Item -Force -Name "$DataDir\$BenchmarkName" -ItemType Directory | Out-Null
New-Item -Force -Name "$PlotDir" -ItemType Directory | Out-Null
New-Item -Force -Name "$PlotDir\$BenchmarkName" -ItemType Directory | Out-Null
New-Item -Force -Name "$plotDirNormal" -ItemType Directory | Out-Null
New-Item -Force -Name "$plotDirCompression" -ItemType Directory | Out-Null

Write-Output "Start test for $($BenchmarkName)"
Start-Process java -ArgumentList "-jar $pathToJar -i $inputFile -o $outputFile -d $dataFile -p $plotDirNormal -iter $Iterations -r $resultFileNameNormal -s $solutionRouteFileFull -n $NumberOfRuns -g $generalInfoFileFull" -Wait -NoNewWindow
Start-Process java -ArgumentList "-jar $pathToJar -i $inputFile -o $outputFileCompression -d $dataFileCompression -p $plotDirCompression -iter $Iterations -c -r $resultFileNameCompression -s $solutionRouteFileCompressed -dc greedy -n $NumberOfRuns -g $generalInfoFileCompressed" -Wait -NoNewWindow
Write-Output "End test for $($BenchmarkName)"