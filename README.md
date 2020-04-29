# Description

This repository is the final project for the Spring/2020, 02211 - Advanced Computer Architecture course at the Technical University of Denmark, lectured by Prof. Martin Schoeberl.

# Project

This project is a implementation of a 32-bit pipeline RISC-V processor designed for low-power Spacial applications.

# Requirements

 * A recent version of Java (JDK 1.8)

 * The Scala build tool [sbt](http://www.scala-sbt.org/)

# Running

make riscv
	Generates the Verilog files for the small ALU.
	Synthesize it for the altde2-115 board.

See the Makefile for further examples, or simply run `sbt run` to see all objects with a main.

# Installing the RISCV gnu toolchain

**See this:** https://mindchasers.com/dev/rv-getting-started (keep in mind it's not totally uptodate)

Step-by-step intstructions to install the toolchain for the RISCVi user set ISA for Ubuntu.
You should execute these commands from a directory, that is NOT the aca-riscv solutions.

1. sudo apt-get install autoconf automake autotools-dev curl python3 libmpc-dev libmpfr-dev libgmp-dev gawk build-essential bison flex texinfo gperf libtool patchutils bc zlib1g-dev libexpat-dev
2. sudo apt-get update 
3. sudo mkdir /opt/riscv32i
4. git clone --recursive https://github.com/riscv/riscv-gnu-toolchain
5. cd riscv-gnu-toolchain/
6. mkdir build; cd build
7. ../configure --prefix=/opt/riscvi --with-arch=rv32i
8. make
9. export PATH=/opt/riscv32i/bin:$PATH

**Note:** steps #4 and #8 take a long time. 
**Note 2:**Step #9 needs to be repeated every time you restart. To add the toolchain permanently to you path variable, do the following:
1. nano ~/.bashrc
2. Add this `PATH=/opt/riscv32i/bin:$PATH` to the bottom of the file and Save and Exit by pressing Ctrl+x.


# Compiling a file into machine code
Quick guide on how to do it manually, taken from Martin's lab. (should be replaced with CMake)
Start in root directory.
1. riscv32-unknown-elf-gcc -S ctests/foo.c -o ctests/foo.s -O0
2. riscv32-unknown-elf-gcc -c ctests/foo.s -o ctests/foo.o -O0
3. riscv32-unknown-elf-objcopy ctests/foo.o --dump-section .text=ctests/output.bin

## Print machine code to use in RISCV test:
xxd -b -c 4  ctests/output.bin | awk -v q='"' '{ printf "" q "b%s%s%s%s" q ",\n", $2, $3, $4, $5}'
- *ctests/output.bin* is the output from #3
- You can take the machine code and paste it into the array in **RiscvTest**.