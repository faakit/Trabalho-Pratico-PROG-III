import java.util.HashMap;
import java.util.Map;

public class Estudo extends Atividade {

    private static final long serialVersionUID = 1L;
    
    private Map<String, String> materiais;
    
    public Estudo(int numero, String nome, Map<String, String> materiais){
        super(numero, nome, false, 2);
        this.materiais = new HashMap<>();
    }

    public void addMateriais(String nome, String url){
        this.materiais.put(nome, url);
    }
    
    public Map<String, String> getMateriais(){
        return this.materiais;
    }

}
