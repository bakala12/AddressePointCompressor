$inputDir = "Benchmarks"
$outputDir = "Results\Output"
$dataDir = "Results\Data"
$plotDir = "Results\Plots"
$resultFile = "results.csv"

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

$stream = $null

Try{
	$currDir = Split-Path -Path $MyInvocation.MyCommand.Path

	$stream = [System.IO.StreamWriter] "$currDir\$resultFile"
	[Void]$stream.WriteLine("BenchmarkName,Best known solution,Original size,Original solution vehicles,Original solution value,Original solution time,Compression size,Compression ratio,Compression solution,Compression solution quality loss,Compression time,Compression solution time,Time boost")
	Write-Output "Performing benchmark tests"
	foreach($benchmark in Get-ChildItem $inputDir){
		$benchmarkName = $benchmark.BaseName
		Write-Output $benchmarkName
		.\runtest -BenchmarkName $benchmarkName -InputDir $inputDir -OutputDir $outputDir -DataDir $dataDir -PlotDir $plotDir
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