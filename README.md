# 4102-Tarea1

En esta tarea se realizan experimentos sobre la implementación de la
estructura de datos **R-Tree**, y sus heurísticas de manejo de overflow
**Greene's split** y **Linear split**.

## Datos del sistema para el experimento

* Sistema Operativo: Ubuntu 18.04 64 bits
* RAM: 12 GB
* Tamaño del disco: partición de 196 GB 
* Tipo de Disco: Disco mecánico HDD (SATA), 5400 RPM.
* Tamaño página de disco 4096 bytes ó 4kB
* Compilador de la JVM: java version 1.8.0_191

Se obtiene el tamaño de una página de disco con el siguiente comando:

	$ getconf PAGESIZE
	4096

**`$` indica la entrada de la consola.**

## Ejecución

El código de la implementación y todo lo necesario para realizar los
experimentos se encuentra en el directorio **rtree**.

Los experimentos generarán archivos dentro de las carpetas datosdummy,
datosgreene y datoslinear, estas estan contenidas dentro del
directorio rtree. Para ejecutar los experimentos se usan los siguientes
comandos:

	$ cd rtree
	$ javac -sourcepath src/ -d compiled/ src/Main.java
	$ java -classpath compiled/ Main `arg1`

donde `arg1` es la cantidad de bytes que tiene la página de disco del computador donde se ejecuta el experimento. Por ejemplo, en el caso de este ordenador, debo correr:

	$ java -classpath compiled/ Main 4096
