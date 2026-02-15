using UnityEngine;

public class PipeController : MonoBehaviour
{
    public float speed;

    // Variable que representa el límite en x en el que la tubería debe desaparecer y aparecer al lado contrario
    public float xLimit;

    // Variable que representa el mínimo y máximo de altura de la tubería
    public float yVariance;

    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if (transform.position.x < -xLimit)
        {
            // Genero un número aleatorio de altura para la tubería dentro del rango de los límites mínimo y máximo
            float alturaAleatoria = Random.Range(-yVariance, yVariance);
            transform.position = new Vector3(xLimit, alturaAleatoria, 0.0f);
            //tuberiasPasadas++;
        }
        transform.position += new Vector3(-speed * Time.deltaTime, 0.0f, 0.0f);
    }
}
