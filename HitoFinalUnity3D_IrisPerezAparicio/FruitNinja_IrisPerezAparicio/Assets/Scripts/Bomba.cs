using UnityEngine;

public class Bomb : MonoBehaviour
{
    private void OnTriggerEnter(Collider objetoQueColisiona)
    {
        // Si el objeto que colisiona tiene la etiqueta "Player"
        // (es decir, si es la blade)
        if (objetoQueColisiona.CompareTag("Player"))
        {
            GetComponent<Collider>().enabled = false;
            GameManager.Instance.Explosion(); // Enviamos la señal de explosion al GameManager
        }
    }

}
