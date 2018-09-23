Param(
	[Parameter(Mandatory=$true)]
	$BenchmarkName,
	[Parameter(Mandatory=$true)]
	$ResultDir
)

$currDir = Split-Path -Path $MyInvocation.MyCommand.Path

$normalResultPath = "$currDir\$ResultDir\$BenchmarkName\$($BenchmarkName)Full.result"
$compressedResultPath = "$currDir\$ResultDir\$BenchmarkName\$($BenchmarkName)Compressed.result"

$i=0
$bestKnown = ""
$orgSize = ""
$orgSolVal = ""
$orgSolTime = ""
$orgSolVeh = ""
foreach($line in Get-Content $normalResultPath) {
	if($i -eq 2){
		$bestKnown = $line
	}
	if($i -eq 3){
		$orgSize = $line
	}
	if($i -eq 4){
		$orgSolVal = $line
	}
	if($i -eq 5){
		$orgSolTime = $line
	}
	if($i -eq 6){
		$orgSolVeh = $line
	}
	$i = $i + 1
}
$i=0
$compSize = ""
$compSol = ""
$compTime = ""
$compSolTime = ""
foreach($line in Get-Content $compressedResultPath) {
	if(($i -le 7)){
		$compSize = $line
	}
	if(($i -le 4)){
		$compSol = $line
	}
	if(($i -le 8)){
		$compTime = $line
	}
	if(($i -le 5)){
		$compSolTime = $line
	}
	$i = $i + 1
}

# Output:
# BenchmarkName
# Best known solution
# Original size
# Original solution vehicles
# Original solution value
# Original solution time
# Compression size
# Compression ratio (*)
# Compression solution
# Compression solution quality loss (*)
# Compression time
# Compression solution time
# Time boost (*)

$compRatio = $compSize / $orgSize * 100
$compQualityLoss = ($compSol - $orgSolVal) / ($orgSolVal) * 100
$timeBoost = ($orgSolTime - $compTime - $compSolTime) / $orgSolTime * 100

$builder = [System.Text.StringBuilder]::new()
[Void]$builder.Append($BenchmarkName)
[Void]$builder.Append(",")
[Void]$builder.Append($bestKnown)
[Void]$builder.Append(",")
[Void]$builder.Append($orgSize)
[Void]$builder.Append(",")
[Void]$builder.Append($orgSolVeh)
[Void]$builder.Append(",")
[Void]$builder.Append($orgSolVal)
[Void]$builder.Append(",")
[Void]$builder.Append($orgSolTime)
[Void]$builder.Append(",")
[Void]$builder.Append($compSize)
[Void]$builder.Append(",")
[Void]$builder.Append("$($compRatio)%")
[Void]$builder.Append(",")
[Void]$builder.Append($compSol)
[Void]$builder.Append(",")
[Void]$builder.Append("$($compQualityLoss)%")
[Void]$builder.Append(",")
[Void]$builder.Append($compTime)
[Void]$builder.Append(",")
[Void]$builder.Append($compSolTime)
[Void]$builder.Append(",")
[Void]$builder.Append("$($timeBoost)%")

$resultLine = $builder.ToString()
return $resultLine