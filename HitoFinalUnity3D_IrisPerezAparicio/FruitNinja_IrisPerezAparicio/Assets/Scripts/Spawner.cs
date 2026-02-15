using UnityEngine;
using TMPro;

public class Spawner : MonoBehaviour
{
    private Collider areaSpawn;

    public GameObject[] prefabsFrutas;
    public GameObject prefabBomba;

    [Range(0f, 1f)]
    public float probabilidadBomba = 0.05f;

    public int scoreAumentoNivel = 20; // Cada cuántos puntos se aumenta el nivel 
    public float aumentoProbabBomba = 0.02f; // Cuánto se incrementa la probabilidad de bomba cada vez
    public float maxProbabBomba = 0.35f; // Establezco un porcentaje máximo de probabilidad de bombas
    public float reduccionDelay = 0.05f; // Cuánto se reduce el delay de aparición entre objetos cada vez
    public float delayMinimo = 0.1f; // Delay mínimo de aparición de objetos (límite de velocidad de aparición)

    public float delayMinimoSpawn = 0.25f;
    public float delayMaximoSpawn = 1f;

    public float anguloMinimo = -15f;
    public float anguloMaximo = 15f;

    public float fuerzaMinima = 18f;
    public float fuerzaMaxima = 22f;

    public float tiempoMaximoVida = 5f;

    public TMP_Text nivelText;


    private float timer;
    private float spawnDelay;
    private float delayInicial = 0.5f;
    private bool spawnerIniciado = false;
    private int nivel = 0;
    private float probabilidadBombaInicial;
    private float delayMinimoInicial;
    private float delayMaximoInicial;


    void Awake()
    {
        areaSpawn = GetComponent<Collider>();

        // Guardo los valores iniciales de probabilidad de bomba y delay
        probabilidadBombaInicial = probabilidadBomba;
        delayMinimoInicial = delayMinimoSpawn;
        delayMaximoInicial = delayMaximoSpawn;

        spawnDelay = Random.Range(delayMinimoSpawn, delayMaximoSpawn);
    }

    void Update()
    {
        timer += Time.deltaTime;

        // Espera inicial 
        if (!spawnerIniciado)
        {
            if (timer >= delayInicial)
            {
                spawnerIniciado = true;
                timer = 0f;
            }
            return;
        }

        // Spawn de frutas con delay aleatorio en un rango
        if (timer >= spawnDelay)
        {
            SpawnearObjeto();
            timer = 0f;
            spawnDelay = Random.Range(delayMinimoSpawn, delayMaximoSpawn);
        }
    }

    private void SpawnearObjeto()
    {
        GameObject prefab = prefabsFrutas[Random.Range(0, prefabsFrutas.Length)];

        if (Random.value < probabilidadBomba)
        {
            prefab = prefabBomba;
        }

        Vector3 posicion = new Vector3(
            Random.Range(areaSpawn.bounds.min.x, areaSpawn.bounds.max.x),
            Random.Range(areaSpawn.bounds.min.y, areaSpawn.bounds.max.y),
            Random.Range(areaSpawn.bounds.min.z, areaSpawn.bounds.max.z)
        );

        // Establezco un rango de rotación en el eje z
        // para que las frutas salgan disparadas en diferentes angulos
        Quaternion rotacion = Quaternion.Euler(0f, 0f, Random.Range(anguloMinimo, anguloMaximo));

        // Se instancia una de las frutas del array de prefabs (prefab) o una bomba
        // Con una posición dentro del rango del spawn area (position)
        // Con un ángulo de rotación en el eje z (rotation)
        GameObject objetoInstanciado = Instantiate(prefab, posicion, rotacion);

        // Se destruye el objeto a los x segundos
        Destroy(objetoInstanciado, tiempoMaximoVida);

        // Impulso el objeto creado hacia arriba con una fuerza aleatoria dentro de un rango
        float force = Random.Range(fuerzaMinima, fuerzaMaxima);
        objetoInstanciado.GetComponent<Rigidbody>().AddForce(objetoInstanciado.transform.up * force, ForceMode.Impulse);
    }

    public void ActualizarDificultad(int score)
    {
        // Calcular el nivel
        int nivelNuevo = score / scoreAumentoNivel;

        if (nivelNuevo > nivel)
        {
            // Aumenta la probabilidad de bomba (sin pasar de un máximo de probabilidad)
            probabilidadBomba += aumentoProbabBomba;
            if (probabilidadBomba > maxProbabBomba)
            {
                probabilidadBomba = maxProbabBomba;
            }

            // Se reduce el delay de spawn entre frutas (manteniendo un delay mínimo)
            if (delayMinimoSpawn > delayMinimo)
            {
                delayMinimoSpawn -= reduccionDelay;
                if (delayMinimoSpawn < delayMinimo) delayMinimoSpawn = delayMinimo;
            }

            if (delayMaximoSpawn > delayMinimo)
            {
                delayMaximoSpawn -= reduccionDelay;
                if (delayMaximoSpawn < delayMinimo) delayMaximoSpawn = delayMinimo;
            }

            // Actualizo el nivel
            nivel = nivelNuevo;
            ActualizarNivelText();
        }
    }

    public void ResetearDificultad()
    {
        nivel = 0;
        probabilidadBomba = probabilidadBombaInicial;
        delayMinimoSpawn = delayMinimoInicial;
        delayMaximoSpawn = delayMaximoInicial;
        spawnerIniciado = false;
        timer = 0f;

        ActualizarNivelText();
    }

    private void ActualizarNivelText()
    {
        if (nivelText != null) nivelText.text = "Nivel: " + nivel.ToString();
    }
}
