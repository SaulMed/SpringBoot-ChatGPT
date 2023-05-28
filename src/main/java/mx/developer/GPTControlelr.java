package mx.developer;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.flashvayne.chatgpt.dto.ChatRequest;
import io.github.flashvayne.chatgpt.dto.ChatResponse;
import io.github.flashvayne.chatgpt.service.ChatgptService;

@RestController
@RequestMapping("/chatboot")

//Ruta Consulta basica: localhost:8080/chatboot/chat?mensaje=cualquier consulta
//Ruta Consulta avanzada: localhost:8080/chatboot/prompt?mensaje=cualquier consulta
public class GPTControlelr implements InitializingBean {
	
	//Habilitar el bean ChatGptService en el contexto de Spring
	@Autowired
	private ChatgptService gptService;

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("---- Iniciando Chat GTP Boot ----");		
	}
	
	@GetMapping("/chat")
	public String chatBasic(@RequestParam String mensaje) {
		System.out.println(mensaje);
		return gptService.sendMessage(mensaje);
	}
	
	@GetMapping("/prompt")
	public ChatResponse prompt(@RequestParam String mensaje) {
		Integer maxToken = 300;
		String model = "text-davinci-003";
		Double temperature = 0.5;
		Double topP = 1.0;
		ChatRequest request = new ChatRequest(model, mensaje, maxToken, temperature, topP);
		ChatResponse response = gptService.sendChatRequest(request);
		System.out.println("Respuesta de OpenAI : " + response.toString());
		return response;
	}

}
