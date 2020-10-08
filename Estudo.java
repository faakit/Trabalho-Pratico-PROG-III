import java.util.HashMap;
import java.util.Map;

public class Estudo extends Atividade {
    Map<String, String> materiais;
    
    public Estudo(int numero, String nome, Map<String, String> materiais){
        super(numero, nome);
        this.materiais = new HashMap<>();
    }
    
}
