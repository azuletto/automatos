package maquinas;

import pattern.Patterns;

public class AP {
    
    Integer topo = 0, estadosFinais[], estadoInicial, estadoAtual;
    String pilha[], dados[], type = "P";
    Patterns patterns[];

    public AP(String[] specs, String[] input) {
        //construtores da classe
        this.dados = input;
        String estadoInicial = specs[1];
        this.estadoInicial = Integer.parseInt(estadoInicial);
        this.pilha = new String[100];
        String[] estadosFinais = specs[2].split(",");
        this.estadosFinais = new Integer[estadosFinais.length];
        for (int i = 0; i < estadosFinais.length; i++) {
            this.estadosFinais[i] = Integer.parseInt(estadosFinais[i]);
        }

        String[] patterns = specs[3].split(";");
        this.patterns = new Patterns[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            this.patterns[i] = new Patterns(patterns[i], type);
        }
    }

    public Boolean run() {

        //metodo que executa a maquina
        System.out.println("Estado Inicial em -> "+estadoInicial);
        for (int i = 0; i < dados.length; i++) {
            String input = dados[i];
            estadoAtual = estadoInicial;
            
            
            for (int j = 0; j < input.length(); j++) {
                estadoAtual = proximoEstado(estadoAtual, input.charAt(j));
                

                //verifica se o estado atual eh nulo ou seja se há transição
                System.out.println("O Estado eh -> "+estadoAtual+" ---- Simbolo: "+input.charAt(j));
                if(estadoAtual == null) {
                    System.out.println("REJEITADO POR NAO TER TRANSICAO");
                    return false;
                }
            }
        }
        //verifica se o estado atual eh final        
        if (isFinal(estadoAtual)) {
            System.out.println("ACEITA");
            return true;
        } else {
            System.out.println("REJEITA");
            return false;
        }
    }
    public Integer proximoEstado(Integer estadoAtual, Character simbolo) {
        for (int i = 0; i < patterns.length; i++) {
            if (patterns[i].estado == estadoAtual && patterns[i].simbolo.equals(simbolo.toString()) || simbolo == '_') {
                desempilha(patterns[i],i);
                empilha(patterns[i],i);
                return patterns[i].proximoEstado;
            }
        }
        return null;
    }
    public void empilha(Patterns patterns, Integer i) {

            if (patterns.estado == estadoAtual && patterns.empilha != "_") {
                System.out.println("Empilhou: "+patterns.empilha);
                this.pilha[topo] = patterns.empilha;
                topo++;
            } else {
                System.out.println("Nao empilhou");
            }
        
    }
    public void desempilha(Patterns patterns, Integer i) {
        if(topo == 0 && patterns.desempilha != "_") {
            System.out.println("Pilha vazia");
            return;
        }
        if (patterns.estado == estadoAtual && patterns.desempilha != "_") {
            System.out.println("Desempilhou: "+patterns.desempilha);
            pilha[topo] = null;
            topo--;
        } else {
            System.out.println("Nao desempilhou");
        }
    }
    public Boolean isFinal(Integer estadoAtual) {
        for (int i = 0; i < estadosFinais.length; i++) {
            if (estadosFinais[i] == estadoAtual) {
                return true;
            }
        }

        return false;
    }
}
