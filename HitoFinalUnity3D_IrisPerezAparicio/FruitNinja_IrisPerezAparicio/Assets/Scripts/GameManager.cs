using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance;

    public Blade blade;
    public Spawner spawner;
    public Text scoreText;

    public int score = 0;
    public TMPro.TMP_Text bestScoreText;

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
        scoreText.text = score.ToString(); // Establece el score en 0
        ActualizarTextoBestScore();
    }

    // Elimina los objetos existentes (frutas / bombas)
    void LimpiarEscena()
    {
        foreach (Fruit f in FindObjectsOfType<Fruit>()) Destroy(f.gameObject);
        foreach (Bomb b in FindObjectsOfType<Bomb>()) Destroy(b.gameObject);
    }

    public void AumentarScore(int points)
    {
        score += points;
        scoreText.text = score.ToString();

        spawner.ActualizarDificultad(score);

        if (score > PlayerPrefs.GetInt("bestScore", 0))
            PlayerPrefs.SetInt("bestScore", score);
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
