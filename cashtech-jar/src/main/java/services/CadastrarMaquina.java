/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.processos.ProcessoGrupo;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.group.rede.RedeInterfaceGroup;
import com.github.britooo.looca.api.group.rede.RedeParametros;
import com.github.britooo.looca.api.group.sistema.Sistema;
import java.util.ArrayList;
import java.util.List;
import repositories.MaquinaRepository;

/**
 *
 * @author murilo
 */
public class CadastrarMaquina {

    Looca looca = new Looca();
    Sistema sistema = looca.getSistema();
    Memoria memoria = looca.getMemoria();
    Processador processador = looca.getProcessador();
    DiscoGrupo grupoDeDiscos = looca.getGrupoDeDiscos();
    Rede rede = looca.getRede();
    ProcessoGrupo grupoDeProcessos = looca.getGrupoDeProcessos();
    RedeParametros parametros = rede.getParametros();
    RedeInterfaceGroup redeInterfaceGroup = rede.getGrupoDeInterfaces();
    List<RedeInterface> redeInterfaces = redeInterfaceGroup.getInterfaces();

    MaquinaRepository executar = new MaquinaRepository();

    public Integer executarCadastro() {
        executar.cadastrarSistema(sistema);
        executar.cadastrarEndereco();
        Integer idATM = executar.cadastrarMaquina(parametros);

        // =============== Cadastrar Processos permitidos ================
        // Lista de processos permitidos são os primeiros processos que carrega
        List<Processo> processosPermitidos = new ArrayList(grupoDeProcessos.getProcessos());

        // Passar esses processos para apenas o nome
        List<String> nomeProcessosPermitidos = new ArrayList();
        for (Processo processo : processosPermitidos) {
            if (!nomeProcessosPermitidos.contains(processo.getNome())) {
                nomeProcessosPermitidos.add(processo.getNome());
            }
        }
        System.out.println(nomeProcessosPermitidos);
        executar.cadastrarProcessosPermitidosPadrao(nomeProcessosPermitidos);
        // ==================================================================
        return idATM;
    }

}