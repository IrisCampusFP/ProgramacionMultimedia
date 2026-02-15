using UnityEngine;

public class Blade : MonoBehaviour
{
    public float fuerzaCorte = 5f;
    public float velocidadMinimaCorte = 0.01f;

    private Camera camara;
    private Collider sliceCollider;
    private TrailRenderer corteTrail;


    public AudioSource bladeSFX;

    public float velocidadMinimaSonido = 30f;
    public float tiempoEntreSonidos = 0f;

    private float ultimoSonido;


    public Vector3 direction { get; private set; }
    public bool slicing { get; private set; }

    void Awake()
    {
        camara = Camera.main;
        sliceCollider = GetComponent<Collider>();
        corteTrail = GetComponentInChildren<TrailRenderer>();

        StopSlice();
    }

    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            StartSlice();
        }
        else if (Input.GetMouseButtonUp(0))
        {
            StopSlice();
        }
        else if (slicing)
        {
            ContinueSlice();
        }
    }

    private void StartSlice()
    { 
        // Obtengo la posición del ratón y la transformo en un punto del mundo 
        Vector3 position = camara.ScreenToWorldPoint(Input.mousePosition);
        position.z = 0f; 
        transform.position = position;

        slicing = true;
        sliceCollider.enabled = true;
        corteTrail.enabled = true;
        corteTrail.Clear();
    }

    private void StopSlice()
    {
        slicing = false;
        sliceCollider.enabled = false;
        corteTrail.enabled = false;
    }

    private void ContinueSlice()
    {
        // Obtengo la posición del ratón y la transformo en un punto del mundo 
        Vector3 newPosition = camara.ScreenToWorldPoint(Input.mousePosition);
        newPosition.z = 0f;
        direction = newPosition - transform.position;

        float velocity = direction.magnitude / Time.deltaTime;

        // Solo colisiona (corta) si la velocidad es mayor a la minima
        sliceCollider.enabled = velocity > velocidadMinimaCorte;

        transform.position = newPosition;

        if (velocity > velocidadMinimaSonido && Time.time - ultimoSonido > tiempoEntreSonidos)
        {
            bladeSFX.PlayOneShot(bladeSFX.clip);
            ultimoSonido = Time.time;
        }
    }
}
