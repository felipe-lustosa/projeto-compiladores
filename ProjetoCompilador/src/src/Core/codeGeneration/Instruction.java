package Core.codeGeneration;

public class Instruction {
  public byte op; // op-code (0 .. 15)
  public byte r; // register field (0 .. 15)
  public byte n; // length field (0 .. 255)
  public short d; // operand field (-32767 .. +32767)
  
  public static final byte // op-codes (Table C.2)
    LOADop = 0, LOADAop = 1,
    LOADIop = 2, LOADLop = 3,
    STOREop = 4, STOREIop = 5,
    CALLop = 6, CALLIop = 7,
    RETURNop = 8, PUSHPARAMop = 9,
    PUSHop = 10, POPop = 11,
    JUMPop = 12, JUMPIop = 13,
    JUMPIFop = 14, HALTop = 15;

  public static final byte // registers (Table C.1)
    CBr = 0, CTr = 1, PBr = 2, PTr = 3,
    SBr = 4, STr = 5, HBr = 6, HTr = 7,
    LBr = 8, L1r = 9, L2r = 10, L3r = 11,
    L4r = 12, L5r = 13, L6r = 14, CPr = 15;

  public Instruction(byte op, byte r, byte n, short d) {
    this.op = op;
    this.r = r;
    this.n = n;
    this.d = d;
  }

  private Instruction[] code = new Instruction[1024];
  private short nextInstrAddr = 0;

  private void emit (byte op, byte r, byte n, short d) {
    code[nextInstrAddr++] = new Instruction(op, r, n, d);
  }

  public Object visitProgram (Program prog, Object arg) {
    prog.C.visit(this, arg);
    emit(Instruction.HALTop, 0, 0, 0);
  }

  public Object visitAssignCommand (AssignCommand com, Object arg) {
    com.E.visit(this, arg);
    encodeAssign(com.V);
    return null;
  }

  public Object visitCallCommand (CallCommand com, Object arg) {
    com.E.visit(this, arg);
    short p = address of primitive routine named com.I;
    emit(Instruction.CALLop, Instruction.SBr, Instruction.PBr, p);
    return null;
  }

  public Object visitSequentialCommand (SequentialCommand com, Object arg) {
    com.C1.visit(this, arg);
    com.C2.visit(this, arg);
    return null;
  }

  public Object visitLetCommand (LetCommand com, Object arg) {
    com.D.visit(this, arg);
    com.C.visit(this, arg);
    short s = amount of storage allocated by D;
    if(s > 0)
      emit(Instruction.POPop, (byte)0, (byte)0, s);
    return null;
  }

  public Object visitIfCommand (Ifcommand com, Object arg)  {
    com. E.visit (this, arg) ;
    short i = nextInstrAddr;
    emit(Instruction.JUMPIFop, (byte)0, Instruction.CBr, (short)0);
    com.Cl.visit(this, arg);
    short j = nextInstrAddr;
    emit(Instruction.JUMPop, (byte)0, Instruction.CBr, (short)0);
    short g = nextInstrAddr;
    patch(i, 9);
    com.C2 .visit (this, arg) ;
    short h = nextInstrAddr;
    patch(j, nextInstrAddr);
    return null;
  }

  public Object visitWhileCommand (WhileCommand com, Object arg) {
    short j1 = nextInstrAddr;
    com.E.visit(this, arg);
    short j2 = nextInstrAddr;
    emit(Instruction.JUMPIFop, (byte)0, Instruction.CBr, (short)0);
    com.C.visit(this, arg);
    emit(Instruction.JUMPop, (byte)0, Instruction.CBr, j1);
    code[j2].d = nextInstrAddr;
    return null;
  }

  public Object visitIntegerExpression (IntegerExpression expr, Object arg) {
    short v = valuation(expr.IL.spelling);
    emit(Instruction.LOADLop, (byte)0, (byte)0, v);
    return null;
  }

  public Object visitVnameExpression (VnameExpression expr, Object arg) {
    encodeFetch(expr.V);
    return null;
  }

  public Object visitUnaryExpression (UnaryExpression expr, Object arg) {
    expr.E.visit(this, arg);
    short p = address of primitive routine named expr.O.spelling;
    emit(Instruction.CALLop, Instruction.SBr, Instruction.PBr, p);
    return null;
  }

  public Object visitBinaryExpression (BinaryExpression expr, Object arg) {
    expr.E1.visit(this, arg);
    expr.E2.visit(this, arg);
    short p = address of primitive routine named expr.O.spelling;
    emit(Instruction.CALLop, Instruction.SBr, Instruction.PBr, p);
    return null;
  }

  public Object visitConstDeclaration (ConstDeclaration decl, Object arg) {
    short gs = shortValueOf(arg);
    if (dec1.E instanceof IntegerExpression) {
      IntegerLiteral IL = ((IntegerExpression) dec1.E) .IL;
      decl.entity = new KnownValue(1, valuation(IL.spel1ing));
      return new Short(0);
    } else {
      short s = shortValueOf(
      decl.E.visit(this, arg));
      decl.entity = new Unknownvalue(s, gs);
      return new Short(s) ;
    }
  }

  public Object visitVarDeclaration (VarDeclaration decl, Object arg) {
    short gs = shortValueOf(arg);
    short s = shortValueOf(decl.T.visit(this, null));
    emit(Instruction.PUSHop, (byte)0, (byte)0, s);
    decl.entity = new KnownAddress(1, gs);
    return new Short(s) ;
  }

  public Object visitSequentialDeclaration (SequentialDeclaration decl, Object arg) {
    short gs = shortValueOf(arg);
    short sl = shortValueOf(
    decl.Dl.visit(this, arg));
    short s2 = shortValueOf(decl.D2.visit(this, new Short(gs + sl)));
    return new Short(sl + s2);
  }

  public Object visitLetCommand (LetCommand com, Object arg) {
    short gs = shortValueOf(arg);
    short s = shortValueOf(com.D.visit(this, arg));
    com.C.visit(this, new Short(gs + s));
    if(s > 0)
      emit(Instruction.POPop, (byte)0, (byte)0, s);
    return null;
  }

  private void encodeAssign (Vname vname, short s) {
    RuntimeEntity entity = (RuntimeEntity) vname.visit(this, null);
    short d = ((KnownAddress) entity).address;
    emit(Instruction.STOREop, (byte)s, Instruction.SBr, d);
  }

  private void encodeFetch (Vname vname, short s) {
    RuntimeEntity entity =
    (RuntimeEntity) vname.visit(this, null);
    if (entity instanceof KnownValue) {
      short v = ((KnownValue) entity) .value
      emit(Instruction.LOADLop, (byte)0, (byte)0, v);
    } else {
      short d = (entity instanceof Unknownvalue) ?
      ((Unknownvalue) entity).address :
      ((KnownAddress) entity) .address;
      emit(Instruction.LOADop, (byte)s, Instruction.SBr, d); 
    }
  }  
  
  public Object visitsimplevname (Simplevname vname, Object arg) {
    return vname.I.decl.entity;
  }

  public void encode (Program prog) {
    prog.visit(this, new Short(0)); 
  }

  // public Object visitCommand
  // (...Command corn, Object arg);
  // // Generate code as specified by 'execute corn'.
  // public Object visitExpression
  // (...Expression expr, Object arg);
  // // Generate code as specified by 'evaluate expr'.
  // public Object visitDeclaration
  // (...Declaration decl, Object arg);
  // // Generate code as specified by 'elaborate decl'. 

  // private void encodeFetch (Vname vname);
  // // Generate code as specified by 'fetch vname'.
  // private void encodeAssign (Vname vname);
  // // Generate code as specified by 'assign vname'. 
  
  // public Object visitprogram (Program prog, Object arg);

}