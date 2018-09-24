Param(
	[Parameter(Mandatory=$true)]
	$BenchmarkName,
	[Parameter(Mandatory=$true)]
	$ResultDir
)

$fullResultPath = "$ResultDir\$BenchmarkName\$($BenchmarkName)Full.info"
$compressedResultPath = "$ResultDir\$BenchmarkName\$($BenchmarkName)Compressed.info"

$fullLinesIncluded = @(0,1,2,5,8,9,10,11,16,17)
$compressedLinesIncluded = @(6,7,8,9,10,11,12,13,14,15,16,17,20,21)
#Headers: 
#Benchmark 
#OptimalSolution
#ProblemSize
#UsedVehicles
#FullSolAvg
#FullSolStDev
#FullSolMin
#FullSolMax
#FullTimeAvg
#FullTimeStDev
#CompressionSize
#CompressionLevel
#SimpleDecompressionSolutionAvg
#SimpleDecompressionSolutionStDev
#SimpleDecompressionSolutionMin
#SimpleDecompressionSolutionMax
#GreedyDecompressionSolutionAvg
#GreedyDecompressionSolutionStDev
#GreedyDecompressionSolutionMin
#GreedyDecompressionSolutionMax
#CompressedProblemTimeAvg
#CompressedProblemTimeStDev
#CompressionTimeAvg
#CompressionTimeStDev

$fullInfo = Get-Content $fullResultPath
$compressedInfo = Get-Content $compressedResultPath

$builder = [System.Text.StringBuilder]::new()
$i=0
foreach($line in $fullInfo) {
	if($fullLinesIncluded.Contains($i)){
		[Void]$builder.Append($line.Split(" ")[1])
		[Void]$builder.Append(",")
	}
	$i = $i + 1
}
$i=0
foreach($line in $compressedInfo) {
	if($compressedLinesIncluded.Contains($i)){
		[Void]$builder.Append($line.Split(" ")[1])
		[Void]$builder.Append(",")
	}
	$i = $i + 1
}
$resultLine = $builder.ToString()
return $resultLine