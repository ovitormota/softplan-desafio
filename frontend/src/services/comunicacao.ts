export const canal = new BroadcastChannel('transacoes_canal');

export const enviarMensagem = (clienteId: number) => {
  canal.postMessage({ clienteId });
};

export const receberMensagem = (callback: (clienteId: number) => void) => {
  canal.onmessage = (event) => {
    if (event.data && event.data.clienteId) {
      callback(event.data.clienteId);
    }
  };
};