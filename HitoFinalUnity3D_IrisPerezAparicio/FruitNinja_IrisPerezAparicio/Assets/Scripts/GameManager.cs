using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;
using TMPro;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance;

    public Blade blade;
    public Spawner spawner;
    public Text scoreText;

    public int score = 0;
    public TMP_Text bestScoreText;

    // Combo
    public float tiempoCombo = 1.2f;
    public TMP_Text comboText;
    
    private int cortesEnCombo = 0;
    private float tiempoInicioCombo = 0f;

    void Start()
    {
        if (Instance != null)
        {
            Destroy(gameObject);
            return;
        }
        Instance = this;

        NuevaPartida();
    }

    void NuevaPartida()
    {
        LimpiarEscena();

        spawner.ResetearDificultad();

        blade.enabled = true; // Activa la cuchilla
        spawner.enabled = true; // Activa el spawner de objetos (frutas / bombas)

        score = 0;
        scoreText.text = score.ToString(); // Actualiza el texto del score al score inicial (0)
        ActualizarTextoBestScore(); // Actualiza el texto del best score

        cortesEnCombo = 0;
        nivelCombo = 0;
    }

    // Elimina los objetos existentes (frutas / bombas)
    void LimpiarEscena()
    {
        foreach (Fruit f in FindObjectsOfType<Fruit>()) Destroy(f.gameObject);
        foreach (Bomb b in FindObjectsOfType<Bomb>()) Destroy(b.gameObject);
    }

    public void AumentarScore(int points)
    {
        ComprobarCombo();

        score += points;
        scoreText.text = score.ToString();

        spawner.ActualizarDificultad(score);

        // Actualiza el best score si se supera
        if (score > PlayerPrefs.GetInt("bestScore", 0))
            PlayerPrefs.SetInt("bestScore", score);
    }

    private int nivelCombo = 0;

    // Registra cada corte de fruta y evalúa si se alcanza un combo
    void ComprobarCombo()
    {
        float tiempoActual = Time.time;

        // Si se supera el tiempo de inicio de un combo, se reinicia el contador
        if (tiempoActual - tiempoInicioCombo > tiempoCombo)
        {
            cortesEnCombo = 1;
            tiempoInicioCombo = tiempoActual;
            nivelCombo = 0;
        }
        else
        {
            cortesEnCombo++;

            // Si se cumple algún combo, sumo su puntuación equivalente
            /* Resto en la suma la puntuación del combo anterior
             * para que no se acumulen los puntos entre combos */

            if (cortesEnCombo >= 10 && nivelCombo < 10)
            {
                score += (10 - nivelCombo);
                nivelCombo = 10;
                MostrarCombo(10);
            }
            else if (cortesEnCombo >= 5 && nivelCombo < 5)
            {
                score += (5 - nivelCombo);
                nivelCombo = 5;
                MostrarCombo(5);
            }
            else if (cortesEnCombo >= 3 && nivelCombo == 0)
            {
                score += 3;
                nivelCombo = 3;
                MostrarCombo(3);
            }
        }
    }

    // Muestra el texto del combo correspondiente
    void MostrarCombo(int puntos)
    {
        scoreText.text = score.ToString(); // Recargo el texto del score mostrado

        if (comboText != null)
        {
            comboText.text = "¡COMBO +" + puntos + "!";
            comboText.enabled = true;
            Invoke("OcultarCombo", 1.2f); // Se vuelve a ocultar el texto
        }
    }

    void OcultarCombo()
    {
        if (comboText != null)
        {
            comboText.enabled = false;
        }
    }

    void ActualizarTextoBestScore()
    {
        int bestScore = PlayerPrefs.GetInt("bestScore", 0);
        bestScoreText.text = "Best score: " + bestScore.ToString();
    }

    public void Explosion()
    {
        blade.enabled = false;
        spawner.enabled = false;

        SceneManager.LoadScene(0); // Redirijo al jugador al menú inicial
    }
}
