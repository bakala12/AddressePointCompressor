$inputDir = "Benchmarks"
$outputDir = "Results\Output"
$dataDir = "Results\Data"
$plotDir = "Results\Plots"

Write-Output "Performing benchmark tests"
foreach($benchmark in Get-ChildItem $inputDir){
	$benchmarkName = $benchmark.BaseName
	Write-Output $benchmarkName
	.\runtest -BenchmarkName $benchmarkName -InputDir $inputDir -OutputDir $outputDir -DataDir $dataDir -PlotDir $plotDir
}
Write-Output "Tests finished"

