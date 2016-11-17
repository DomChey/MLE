public class VM {
	final int MAX = 1000;
	final byte LOAD  = 0; // Reg = #1234
	final byte PUSH  = 1; // push(Reg)
	final byte POP   = 2; // Reg = pop()
	final byte MUL   = 3; // Reg = reg*pop()
	final byte DIV   = 4; // Reg = Reg/pop()
	final byte ADD   = 5; // Reg = Reg+pop()
	final byte SUB   = 6; // Reg = Reg-pop()
	final byte JIH   = 7; // if Reg>0 then pc = pc + pop()
	
	short mem[] = new short[MAX];
	short stack[] = new short[MAX];
	short sp,reg;
	int pc;
	
	VM(){
		pc = 0;	sp = 0;	reg= 0;
	}
	void push(short x){
		if (sp>=0){
			stack[sp++]=x;
		}
	}
	short pop(){
		sp--;
		if (sp>=0){
			return stack[sp];
		}else{
			return 0;
		}
	}
	void simulate(){
		int counter = 0;
		do{
			counter++;
			switch (mem[pc]&7){
				case LOAD:{reg = (short)(mem[pc]>>3); push(reg); pc++; break;}
				case PUSH:{push(reg);  pc++; break;}
				case POP: {reg = pop();pc++;break;}
				case MUL: {reg = (short) (reg*pop());push(reg);pc++;break;}
				case DIV: {short d = pop(); if (d!=0){reg = (short) (reg/d);push(reg);}pc++;break;}
				case ADD: {reg = (short)(reg+pop());push(reg);pc++;break;}
				case SUB: {reg = (short)(reg-pop());push(reg);pc++;break;}
				case JIH: {short d=pop(); if (pc+d>=0)pc = (pc+d)%MAX; else pc++; break;}
			}
			pc = pc%MAX;
		}while(sp>=0 && counter<=10000);	
	}
	
	public static void main(String[] args) {
		VM vm = new VM();
		
		
		for(int i = 0; i< vm.mem.length; i++){
			vm.mem[i]= (byte)(Math.random()*8);
			System.out.println("Random: " + vm.mem[i]);
		}
		vm.simulate();
		vm.simulate();
		for(int i = 0; i< vm.stack.length; i++){
			System.out.println("Stack: " + vm.stack[i]);
		}
		System.out.println("REG:" + vm.reg);
	}
	
}
