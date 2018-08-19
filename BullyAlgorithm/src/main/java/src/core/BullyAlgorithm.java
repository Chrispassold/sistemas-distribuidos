package src.core;

/*
 Implementar de algoritmos distribuídos de eleição conforme estudados em sala de aula. Em
 específico, a equipe deve focar no desenvolvimento dos algoritmos Bully e Anel.

 Especificação:
  a cada 30 segundos um novo processo deve ser criado
  a cada 25 segundos um processo fazer uma requisição para o coordenador
  a cada 100 segundos o coordenador fica inativo
  a cada 80 segundos um processo da lista de processos fica inativo
  dois processos não podem ter o mesmo ID
  dois processos de eleição não podem acontecer simultaneamente

*/

public class BullyAlgorithm {
    private Routines routines;
    private ProcessManager processManager;

    public static void main(String[] args) {
        new BullyAlgorithm();
    }

    public BullyAlgorithm() {
        routines = new Routines();
        processManager = new ProcessManager();

        routines.startNewRoutine(3, processManager::requestToCoordinator);
        routines.startNewRoutine(5, processManager::createProcess, true);
        routines.startNewRoutine(10, processManager::killRandomProcess);
        routines.startNewRoutine(15, processManager::killCoordinator);
    }


}
