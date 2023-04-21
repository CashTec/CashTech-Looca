/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import cashtech.jar.DataBase;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processos.Processo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

/**
 *
 * @author murilo
 */
public class ProcessosRepository {

    DataBase conexao = new DataBase();

    JdbcTemplate con = conexao.getConnection();

    Looca looca = new Looca();
    Processo processo;

    public void cadastrarProcessosPermitidosPadrao(List<String> processos, Integer empresaId) {

        String sql = "INSERT INTO `cashtech`.`ProcessoPermitido` (`id`, `nome`, `empresa_id`) VALUES ";
        for (int i = 0; i < processos.size(); i++) {
            String processo = processos.get(i);
            if (i == processos.size() - 1) {
                sql += String.format("(null,'%s',%d)", processo, empresaId);
            } else {
                sql += String.format("(null,'%s',%d), ", processo, empresaId);
            }
        }
        con.update(sql);
    }

    public List<String> processosPermitidos(Integer empresaId) {
        List<String> processosPermitidos
                = con.query("select nome from ProcessoPermitido where empresa_id = ?",
                        new SingleColumnRowMapper(String.class), empresaId);

        return processosPermitidos;
    }

    public void cadastrarProcessoKilled(Integer idAtm, Processo processo, LocalDateTime dataHora) {
        con.update("INSERT INTO `cashtech`.`Processo` (`id`, `caixa_eletronico_id`, `nome`, "
                + "`pid`,`uso_cpu`, `uso_memoria`, `byte_utilizado`, `memoria_virtual_ultilizada`,"
                + " `id_dead`, `dt_processo`) "
                + "VALUES (NULL,?,?, ?, ?, ?, ?, ?, 1, ?)",
                idAtm, processo.getNome(), processo.getPid(), processo.getUsoCpu(), processo.getUsoMemoria(),
                processo.getBytesUtilizados(), processo.getMemoriaVirtualUtilizada(),
                dataHora);
    }
}
