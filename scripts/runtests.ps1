$inputDir = "..\TestInstances\Test"
$outputDir = "..\Results\Output"
$dataDir = "..\Results\Data"
$plotDir = "..\Results\Plots"
$resultFile = "Results\results.csv"
$numberOfTestsForBenchmark = 1

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

$stream = $null
$currDir = Split-Path -Path $MyInvocation.MyCommand.Path
$currDir = Split-Path -Parent $currDir
$resultCsvPath = Join-Path -Path $currDir -ChildPath $resultFile

Try{
	$stream = [System.IO.StreamWriter] "$resultCsvPath"
	[Void]$stream.WriteLine("Benchmark,OptimalSolution,ProblemSize,UsedVehicles,FullSolAvg,FullSolStDev,FullSolMin,FullSolMax,FullTimeAvg,FullTimeStDev,CompressionSize,CompressionLevel,SimpleDecompressionSolutionAvg,SimpleDecompressionSolutionStDev,SimpleDecompressionSolutionMin,SimpleDecompressionSolutionMax,GreedyDecompressionSolutionAvg,GreedyDecompressionSolutionStDev,GreedyDecompressionSolutionMin,GreedyDecompressionSolutionMax,CompressedProblemTimeAvg,CompressedProblemTimeStDev,CompressionTimeAvg,CompressionTimeStDev")
	Write-Output "Performing benchmark tests"
	foreach($benchmark in Get-ChildItem $inputDir){
		$benchmarkName = $benchmark.BaseName
		Write-Output $benchmarkName
		.\runtest -BenchmarkName $benchmarkName -InputDir $inputDir -OutputDir $outputDir -DataDir $dataDir -PlotDir $plotDir -NumberOfRuns $numberOfTestsForBenchmark
		$res = .\collectResult -BenchmarkName $benchmarkName -ResultDir $outputDir
		[Void]$stream.WriteLine($res)
		[Void]$stream.Flush()
	}
	Write-Output "Tests finished"
}
Catch
{
	$errorMessage = $_.Exception.Message
	Write-Output "Exception: $errorMessage"
}
Finally{
	if($stream -ne $null){
		[Void]$stream.close()
	}
}