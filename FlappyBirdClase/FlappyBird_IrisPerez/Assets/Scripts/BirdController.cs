using TMPro;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.SceneManagement;

public class BirdController : MonoBehaviour
{
    public Rigidbody rb;
    public float gravityForce;
    public float jumpForce;

    public GameObject pauseMenuGo;
    public int score;

    private TMP_Text scoreText;

    public GameObject scoreTextGo;

    public TMP_Text finalScoreText;

    //public float pipeSpeed;
    //public int numTuberiasAumentoVelocidad = 5;
    //private int tuberiasPasadas;
    //private int ultimoAumento;

    private bool pausePanelInterruptor = true;
    private bool isAlive = true;

    public AudioSource sFXGameOver;


    // Start is called once before the first execution of Update after the MonoBehaviour is created
    void Start()
    {
        Debug.Log("Hola soy el Start");

        rb = GetComponent<Rigidbody>();
        sFXGameOver = GetComponent<AudioSource>();

        pauseMenuGo.SetActive(false);
        scoreTextGo.SetActive(true);

        scoreText = scoreTextGo.GetComponent<TMP_Text>();
        finalScoreText = pauseMenuGo.transform.GetChild(0).GetComponent<TMP_Text>();
    }


    // Update is called once per frame
    void Update()
    {
        //Dar una fuerza de gravedad
        rb.AddForce(Vector3.down * gravityForce * Time.deltaTime, ForceMode.Force);

        if (Input.GetKeyDown(KeyCode.Space)) 
        {
            //Reestablezco sus fuerzas a 0 (para eliminar la fuerza hacia abajo de la gravedad en el momento del salto)
            rb.linearVelocity = Vector3.zero;
            //Darle una fuerza, direccion UP (fuerza de salto)
            rb.AddForce(Vector3.up * jumpForce, ForceMode.Impulse);
        }

        if (isAlive)
        {
            if (Input.GetKeyDown(KeyCode.Escape) && pausePanelInterruptor) // pausePanelInterruptor == true
            {
                PauseMenu(true);
                pausePanelInterruptor = false;
            }
            else if (Input.GetKeyDown(KeyCode.Escape) && !pausePanelInterruptor) // pausePanelInterruptor == false 
            {
                PauseMenu(false);
                pausePanelInterruptor = true;
            }
        } else if (Input.GetKeyDown(KeyCode.Space))
        {
            Restart();
        }

        // Cada 10 tuberías pasadas la velocidad aumenta 0.5 sólo una vez
        //if (score % numTuberiasAumentoVelocidad == 0 && tuberiasPasadas != 0 && tuberiasPasadas != ultimoAumento)
        //{
        //    pipeSpeed += 0.5f;
        //    ultimoAumento = tuberiasPasadas;
        //}
    }

    private void OnCollisionEnter(Collision collision)
    {
        Debug.Log("He chocado con " + collision.gameObject.name);
        if(collision.gameObject.tag == "Pipe")
        {
            Debug.Log("Game over :(");

            Death();
        }
    }

    private void OnTriggerExit(Collider other)
    {
        Debug.Log("He pasado por: " + other.gameObject.name);
        score++;
        scoreText.text = score.ToString();
    }

    public void PauseMenu(bool activate)
    {
        if (activate) {
            Time.timeScale = 0.0f;
            scoreTextGo.SetActive(false);
            pauseMenuGo.SetActive(true);
        }
        else
        {
            Time.timeScale = 1.0f;
            scoreTextGo.SetActive(true);
            pauseMenuGo.SetActive(false);
        }
    }

    public void Death()
    {
        isAlive = false;
        finalScoreText.text = score.ToString();
        sFXGameOver.Play();
        Time.timeScale = 0.0f;
        scoreTextGo.SetActive(false);
        pauseMenuGo.SetActive(true);
    }

    public void Restart()
    {
        Time.timeScale = 1f;
        SceneManager.LoadScene("FlappyBird");
    }
}
