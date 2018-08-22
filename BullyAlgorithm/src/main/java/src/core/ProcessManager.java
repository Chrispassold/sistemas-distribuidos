package src.core;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;

/**
 * Concentra as operacoes principais da rotina
 */
public class ProcessManager {
    /**
     * Armazena o identificador do ultimo processo criado
     */
    private static int latestId = 0;

    /**
     * Armazena os processos e seus respectivos identificadores
     * Os processos serão armazenados em ordem crescente em relacao ao identificador
     */
    private TreeMap<Integer, Process> processes;

    /**
     * Armazena o atual coordenador, se for {@code null},
     * o proximo processo se tornará o coordenador
     */
    private static Process coordinator;

    /**
     * Controla quando ha alguma eleicao sendo feita,
     * assim nao ocorre duas eleicoes ao mesmo tempo
     */
    private static boolean isElecting = false;

    public ProcessManager() {
        processes = new TreeMap<>();
    }

    /**
     * Retorna o atual coordenador
     */
    public static Process getCoordinator() {
        return coordinator;
    }

    /**
     * Armazena um novo coordenador
     */
    public synchronized void setCoordinator(Process newCoordinator) {
        coordinator = newCoordinator;
    }

    /**
     * Cria um novo processo e o adiciona na lista de processos, respeitando o {@code lastId}
     */
    public synchronized void createProcess() {
        int id = ++latestId;
        Process process = new Process(id);

        if (getCoordinator() == null) {
            this.setCoordinator(process);
        }

        processes.put(id, process);
        ConsoleUtil.printGreen("Created a new process %s", process);
    }

    /**
     * Retorna um processo aleatorio
     */
    protected Process getRandomProcess() {
        if (processes.isEmpty()) return null;

        Random generator = new Random();
        while (true) {
            int random = generator.nextInt(latestId);
            if ((getCoordinator() == null || random != getCoordinator().getId()) && processes.containsKey(random)) {
                return processes.get(random);
            }
        }
    }

    /**
     * Inativa um processo aleatorio, que nao seja o coordenador
     */
    public void killRandomProcess() {
        Process randomProcess = getRandomProcess();
        if (randomProcess != null) {
            randomProcess.inactive();
            ConsoleUtil.printRed("Killing %s", randomProcess);
        }
    }

    /**
     * Inativa o atual coordenador
     */
    public void killCoordinator() {
        if (getCoordinator() != null) {
            getCoordinator().inactive();
            ConsoleUtil.printRed("Killing coordinator %s", getCoordinator());
        }
    }

    /**
     * Envia uma mensagem para o coordenador
     * <p>
     * Se o coordenador responder positivamente, continua
     * <p>
     * Se o coordenador responder negativamente,
     * o processo enviou a mensagem inicia uma eleicao para o novo coordenador
     * <p>
     */
    public void requestToCoordinator() {
        Process randomProcess = getRandomProcess();
        if (randomProcess != null) {
            boolean ok = randomProcess.sendMessageToCoordinator();

            if (ok || isElecting) return;

            this.startElection(randomProcess);
        }
    }

    /**
     * Inicia uma eleicao de um novo coordenador,
     * isso acontece quando o coordenador responde negativamente a algum processo
     * <p>
     * O processo ativo com o maior identificador será o novo coordenador
     * <p>
     * A eleicao funciona da seguinte forma:
     * 1 - comeca buscando o ultimo identificador existente na lista de processos
     * 2 - verifica se esta ativo
     * caso sim, foi achado o novo coordenador
     * caso nao, é decrementado o ultimo identificador buscado e pesquisado novamente
     *
     * @param whoStarts Processo que iniciou a eleicao
     */
    private void startElection(Process whoStarts) {
        isElecting = true;
        ConsoleUtil.printBlue("Process %d started an election", whoStarts.getId());
        ConsoleUtil.printBlue("%s", Arrays.toString(processes.keySet().toArray()));
        int higherKey = processes.lastKey();
        Process newCoordinator = null;

        while (newCoordinator == null || !newCoordinator.isActive()) {
            if (processes.containsKey(higherKey)) {
                if (higherKey == whoStarts.getId()) {
                    newCoordinator = whoStarts;
                } else {
                    newCoordinator = processes.getOrDefault(higherKey, null);
                }
            }

            higherKey--;
        }

        this.setCoordinator(newCoordinator);
        ConsoleUtil.printBlue("The new coordinator is %s", getCoordinator());

        isElecting = false;
    }

}
