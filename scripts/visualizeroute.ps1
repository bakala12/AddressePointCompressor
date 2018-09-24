Param(
	[Parameter(Mandatory=$true)]
	$InputFile,
	[Parameter(Mandatory=$true)]
	$OutputFile
)

$pathToPython = ""

$pathToPython ".\visualize\visualizeroute.py $InputFile $OutputFile"