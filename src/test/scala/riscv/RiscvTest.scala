package riscv

import chisel3._
import chisel3.iotesters.Driver
import chisel3.iotesters.PeekPokeTester
import org.scalatest._

object RiscvTester {
val param = Array("--target-dir", "generated", "--generate-vcd-output", "on")
}

class RiscvTester(dut: Riscv) extends PeekPokeTester(dut) {
  poke(dut.io.rxd, 0.U)
  poke(dut.io.txd, 0.U)
  poke(dut.io.led, 0.U)

  step(9)


}

class RiscvTest extends FlatSpec with Matchers {

  // 1 Addi x1, x2, 3 //x1= 3: 000000000011 00010 000 00001 0010011
  // 2 Addi x2, x3, 10 //x2 = 10: 000000001010 00011 000 00010 0010011
  // 3 Addi x3, x4, 40 //x3= 40: 000000101000 00100 000 00011 0010011
  // 4 Addi x4, x5, -1 //x4= -1: 111111111111 00101 000 00100 0010011
  // 5 Add x5, x1, x2 // x5= 13: 0000000 00010 00001 000 00101 0110011
  // 6 Sub x6, x2, x3 // x6= -30: 0100000 00011 00010 000 00110 0110011
  // 7 add x7, x1 , x4 // x7= 2: 0000000 00100 00001 000 00111 0110011
  // 8 Sub x8, x1, x4 // x8 = 4: 0100000 00100 00001 000 01000 0110011
  // 9 And x9, x1, x2 // 0011 & 1010 // x9 = 0010 = 2: 0000000 00010 00001 111 01001 0110011
  // 10 And x10, x1, x4 // 0011 & 1111 // x9 = 0011 = 3: 0000000 00100 00001 111 01010 0110011

  "Riscv" should "pass" in {
    Driver.execute(RiscvTester.param,
      () => new Riscv(Array(
        "b00000000001100010000000010010011",
        "b00000000101000011000000100010011",
        "b00000010100000100000000110010011",
        "b11111111111100101000001000010011",
        "b00000000001000001000001010110011",
        "b01000000001100010000001100110011",
        "b00000000010000001000001110110011",
        "b01000000010000001000010000110011",
        "b00000000001000001111010010110011",
        "b00000000010000001111010100110011"
      ))) { c =>
      new RiscvTester(c)
    } should be(true)
  }

}