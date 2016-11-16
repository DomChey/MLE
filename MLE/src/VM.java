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
	
	int mem[] = new int[MAX];
	int stack[] = new int[MAX];
	int pc,sp,reg;
	
	VM(){
		pc = 0;	sp = 0;	reg= 0;
	}
	void push(int x){
		stack[sp++]=x;
	}
	int pop(){
		if (sp>=1)
			sp--;
		return stack[sp];
	}
	void simulate(){
		do{
			switch (mem[pc]&7){
				case LOAD:{reg = mem[pc]>>3;pc++; break;}
				case PUSH:{push(reg);pc++; break;}
				case POP:{reg = pop();pc++;break;}
				case MUL:{reg = reg*pop();pc++;break;}
				case DIV:{reg = reg/pop();pc++;break;}
				case ADD:{reg = reg+pop();pc++;break;}
				case SUB:{reg = reg-pop();pc++;break;}
				case JIH:{if (reg>0){pc = (pc+pop())%MAX;} else {pc++;} break;}
			}
		}while(pc<MAX && sp>=0);	
	}
}
