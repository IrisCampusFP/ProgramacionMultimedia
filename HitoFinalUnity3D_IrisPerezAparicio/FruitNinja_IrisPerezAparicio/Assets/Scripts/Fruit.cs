using UnityEngine;

public class Fruit : MonoBehaviour
{
    public GameObject frutaCompleta;
    public GameObject frutaCortada;
    public AudioSource corteSFX;

    private Rigidbody frutaRigidbody;
    private Collider frutaCollider;
    private ParticleSystem efectoSalpicar;

    public int puntos = 1;


    private void Start()
    {
        frutaRigidbody = GetComponent<Rigidbody>();
        frutaCollider = GetComponent<Collider>();
        efectoSalpicar = GetComponentInChildren<ParticleSystem>();
    }

    private void OnTriggerEnter(Collider objetoQueColisiona)
    {

        // Se verifica que el objeto con el que colisiona la fruta
        // tenga la etiqueta Player que le pusimos a la cuchilla (Blade)
        // para asegurar que solo se produzcan cortes con ese objeto
        if (objetoQueColisiona.CompareTag("Player"))
        {
            Blade blade = objetoQueColisiona.GetComponent<Blade>();
            Slice(blade.direction, blade.transform.position, blade.fuerzaCorte);
        }
    }

    private void Slice(Vector3 direccion, Vector3 posicion, float fuerza)
    {
        // Aumento puntos
        GameManager.Instance.AumentarScore(puntos);

        // Desactivo el collider (solo se puede cortar 1 vez)
        frutaCollider.enabled = false;

        corteSFX.Play();


        // PARTE VISUAL:

        // Desactivo la fruta completa (sin cortar)
        frutaCompleta.SetActive(false);

        // Activo la fruta cortada
        frutaCortada.SetActive(true);
        efectoSalpicar.Play(); // Ejecuto el efecto de salpicar


        // Establezco el ángulo de rotación de la fruta cortada
        // en función de la dirección del corte
        float angle = Mathf.Atan2(direccion.y, direccion.x) * Mathf.Rad2Deg;
        frutaCortada.transform.rotation = Quaternion.Euler(0f, 0f, angle);

        Rigidbody[] mitades = frutaCortada.GetComponentsInChildren<Rigidbody>();


        foreach (Rigidbody mitad in mitades)
        {
            // La velocidad de las mitades será la misma velocidad que traía
            // la fruta completa para así mantener la misma trayectoria
            mitad.linearVelocity = frutaRigidbody.linearVelocity;

            // Aplico una fuerza en la posición del corte a cada mitad
            // en función de la dirección y fuerza de la cuchilla
            mitad.AddForceAtPosition(direccion * fuerza, posicion, ForceMode.Impulse);
        }
    }
}
