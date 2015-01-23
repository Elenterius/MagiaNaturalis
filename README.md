Magia Naturalis

>"One must know Death in order to understand the Meaning of Life"

Gradel Eclipse Setup Help:
- copy the eclipse folder from a forg-src download inside the repository (should contain .metadata)
- run gradle over the cmd line: ``gradlew setupDevWorkspace setupDecompWorkspace eclipse``
- create lib folder or paste the files inside eclipse in the lib folder
	- put ``Thaumcraft-deobf-1.7.10-4.2.3.3`` in lib folder
	- put ``Baubles-deobf-1.7.10-1.0.1.10`` in lib folder
	- You can attach the Thaumcraft & Baubles API as src to the referenced libraries
- Add to your VM Argument: ``-Dfml.coreMods.load=trinarybrain.magia.naturalis.coremod.MNPlugin``

Visit me on IRC ESPER-NET #ChemLab #FortRose #Thaumcraft
