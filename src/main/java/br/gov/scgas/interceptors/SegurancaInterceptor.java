package br.gov.scgas.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.HttpHeaders;

import br.gov.scgas.annotation.Seguranca;

@Seguranca
@Interceptor
public class SegurancaInterceptor {

	@AroundInvoke
	public Object verificaTokenRequisicao(InvocationContext ctx) throws Exception{

		/**
		ctx.getParameters();
		HttpHeaders headers = (HttpHeaders) ctx.getParameters()[0];
		String authorization = headers.getRequestHeader("authorization").get(0);
		System.out.println("Token is : "+authorization);
		if(authorization != null){
			throw new Exception("Token invalido");
		}
		 * 
		 */
		return ctx.proceed();
	}

}
