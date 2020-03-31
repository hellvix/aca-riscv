package riscv

import chisel3._
import chisel3.util._

class ImmGen extends Module {
  val io = IO(new Bundle {
    val InsIn = Input(UInt(32.W))

    val ImmOut = Output(UInt(32.W))
  })

  switch(io.InsIn(6,0)) { //Checks instruction type by optocodes
    //I-type
    is("b0000011".U) {
      io.ImmOut := Cat(Fill(20, io.InsIn(31)), io.InsIn(31, 20))
    }
    is("b0010011".U) {
      io.ImmOut := Cat(Fill(20, io.InsIn(31)), io.InsIn(31, 20))
    }
    is("b1100111".U) {
      io.ImmOut := Cat(Fill(20, io.InsIn(31)), io.InsIn(31, 20))
    }
    //S-type
    is("b0100011".U) {
      io.ImmOut := Cat(Fill(20, io.InsIn(31)), io.InsIn(31, 25), io.InsIn(11, 7))
    }
    //SB-type
    is("b1100011".U) {
      io.ImmOut := Cat(Fill(20, io.InsIn(31)), io.InsIn(31),io.InsIn(7), io.InsIn(30,25),io.InsIn(11, 6))
    }
    //U-type
    is("b0110011".U) {
      io.ImmOut := Cat(Fill(12, io.InsIn(31)), io.InsIn(31, 12))
    }
    //UJ-type
    is("b1101111".U) {
      io.ImmOut := Cat(Fill(12, io.InsIn(31)), io.InsIn(31), io.InsIn(19,12), io.InsIn(20), io.InsIn(30,21))
    }
  }
}