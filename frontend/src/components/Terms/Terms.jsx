import React, { useEffect } from "react";
import { Container, Card } from "react-bootstrap";
import Main from "../Template/main/Main";
import logo from "../../assets/images/Logo2RecruIT.svg";
import "./Terms.css";

const Terms = () => {
  useEffect(() => {
    window.scrollTo({
      top: 0,
      left: 0,
      behavior: "smooth"
    });
  }, []);

  return (
    <Main>
      <Container className="container-terms">
        <Card className="card-terms">
          <img className="logo-terms" src={logo} alt="Logo" />
          <h1 className="principal-terms title-principal">
            Termos e Condições
          </h1>

          <Container className="terms">
            <Card className="terms-card">
              <Card.Text className="terms-text">
                <h2 className="title-terms">
                  1. Aceitação dos Termos e Condições de Uso
                </h2>
                <br></br>
                <span>
                  Ao acessar ao site RecruIT, concorda em cumprir estes termos
                  de serviço, todas as leis e regulamentos aplicáveis ​​e
                  concorda que é responsável pelo cumprimento de todas as leis
                  locais aplicáveis. Se você não concordar com algum desses
                  termos, está proibido de usar ou acessar este site. Os
                  materiais contidos neste site são protegidos pelas leis de
                  direitos autorais e marcas comerciais aplicáveis.
                </span>
              </Card.Text>
              <Card.Text className="terms-text">
                <h2 className="title-terms">2. Política de Privacidade</h2>
                <span>
                  A sua privacidade é importante para nós. É política do RecruIT
                  respeitar a sua privacidade em relação a qualquer informação
                  sua que possamos coletar no site RecruIT, e outros sites que
                  possuímos e operamos. Solicitamos informações pessoais apenas
                  quando realmente precisamos delas para lhe fornecer um
                  serviço. Fazemo-lo por meios justos e legais, com o seu
                  conhecimento e consentimento. Também informamos por que
                  estamos coletando e como será usado. Apenas retemos as
                  informações coletadas pelo tempo necessário para fornecer o
                  serviço solicitado. Quando armazenamos dados, protegemos
                  dentro de meios comercialmente aceitáveis ​​para evitar perdas
                  e roubos, bem como acesso, divulgação, cópia, uso ou
                  modificação não autorizados. Não compartilhamos informações de
                  identificação pessoal publicamente ou com terceiros, exceto
                  quando exigido por lei. O nosso site pode ter links para sites
                  externos que não são operados por nós. Esteja ciente de que
                  não temos controle sobre o conteúdo e práticas desses sites e
                  não podemos aceitar responsabilidade por suas respectivas
                  políticas de privacidade. Você é livre para recusar a nossa
                  solicitação de informações pessoais, entendendo que talvez não
                  possamos fornecer alguns dos serviços desejados. O uso
                  continuado de nosso site será considerado como aceitação de
                  nossas práticas em torno de privacidade e informações
                  pessoais. Se você tiver alguma dúvida sobre como lidamos com
                  dados do usuário e informações pessoais, entre em contacto
                  conosco.
                </span>
              </Card.Text>

              <Card.Text className="terms-text">
                <h2 className="title-terms">3. Política de Cookies RecruIT</h2>
                <br></br>
                <h3 className="subtitle-terms">O que são Cookies?</h3>

                <br></br>
                <span>
                  Como é prática comum em quase todos os sites profissionais,
                  este site usa cookies, que são pequenos arquivos baixados no
                  seu computador, para melhorar sua experiência. Esta página
                  descreve quais informações eles coletam, como as usamos e por
                  que às vezes precisamos armazenar esses cookies. Também
                  compartilharemos como você pode impedir que esses cookies
                  sejam armazenados, no entanto, isso pode fazer o downgrade ou
                  quebrar certos elementos da funcionalidade do site.
                </span>

                <h3 className="subtitle-terms">Como usamos os Cookies?</h3>
                <br></br>
                <span>
                  Você pode impedir a configuração de cookies ajustando as
                  configurações do seu navegador (consulte a Ajuda do navegador
                  para saber como fazer isso). Esteja ciente de que a
                  desativação de cookies afetará a funcionalidade deste e de
                  muitos outros sites que você visita. A desativação de cookies
                  geralmente resultará na desativação de determinadas
                  funcionalidades e recursos deste site. Portanto, é
                  recomendável que você não desative os cookies.
                </span>

                <h3 className="subtitle-terms">Desativar Cookies</h3>
                <br></br>

                <span>
                  Você pode impedir a configuração de cookies ajustando as
                  configurações do seu navegador (consulte a Ajuda do navegador
                  para saber como fazer isso). Esteja ciente de que a
                  desativação de cookies afetará a funcionalidade deste e de
                  muitos outros sites que você visita. A desativação de cookies
                  geralmente resultará na desativação de determinadas
                  funcionalidades e recursos deste site. Portanto, é
                  recomendável que você não desative os cookies.
                </span>

                <h3 className="subtitle-terms">Cookies que definimos</h3>
                <br></br>
                <ul className="terms-list">
                  <li>
                    Cookies relacionados à conta: Se você criar uma conta
                    conosco, usaremos cookies para o gerenciamento do processo
                    de inscrição e administração geral. Esses cookies geralmente
                    serão excluídos quando você sair do sistema, porém, em
                    alguns casos, eles poderão permanecer posteriormente para
                    lembrar as preferências do seu site ao sair.
                  </li>
                  <li>
                    Cookies relacionados ao login: Utilizamos cookies quando
                    você está logado, para que possamos lembrar dessa ação. Isso
                    evita que você precise fazer login sempre que visitar uma
                    nova página. Esses cookies são normalmente removidos ou
                    limpos quando você efetua logout para garantir que você
                    possa acessar apenas a recursos e áreas restritas ao efetuar
                    login.
                  </li>
                  <li>
                    Cookies relacionados a boletins por e-mail: Este site
                    oferece serviços de assinatura de boletim informativo ou
                    e-mail e os cookies podem ser usados ​​para lembrar se você
                    já está registrado e se deve mostrar determinadas
                    notificações válidas apenas para usuários inscritos / não
                    inscritos.
                  </li>
                  <li>
                    Pedidos processando cookies relacionados: Este site oferece
                    facilidades de comércio eletrônico ou pagamento e alguns
                    cookies são essenciais para garantir que seu pedido seja
                    lembrado entre as páginas, para que possamos processá-lo
                    adequadamente.
                  </li>
                  <li>
                    Cookies relacionados a pesquisas: Periodicamente, oferecemos
                    pesquisas e questionários para fornecer informações
                    interessantes, ferramentas úteis ou para entender nossa base
                    de usuários com mais precisão. Essas pesquisas podem usar
                    cookies para lembrar quem já participou numa pesquisa ou
                    para fornecer resultados precisos após a alteração das
                    páginas.
                  </li>
                  <li>
                    Cookies relacionados a formulários: Quando você envia dados
                    por meio de um formulário como os encontrados nas páginas de
                    contacto ou nos formulários de comentários, os cookies podem
                    ser configurados para lembrar os detalhes do usuário para
                    correspondência futura.
                  </li>
                  <li>
                    Cookies de preferências do site: Para proporcionar uma ótima
                    experiência neste site, fornecemos a funcionalidade para
                    definir suas preferências de como esse site é executado
                    quando você o usa. Para lembrar suas preferências,
                    precisamos definir cookies para que essas informações possam
                    ser chamadas sempre que você interagir com uma página for
                    afetada por suas preferências.
                  </li>
                </ul>

                <h3 className="subtitle-terms">Cookies de Terceiros</h3>
                <br></br>
                <span>
                  Em alguns casos especiais, também usamos cookies fornecidos
                  por terceiros confiáveis. A seção a seguir detalha quais
                  cookies de terceiros você pode encontrar através deste site.
                  <ul className="terms-list">
                    <li>
                      Este site usa o Google Analytics, que é uma das soluções
                      de análise mais difundidas e confiáveis ​​da Web, para nos
                      ajudar a entender como você usa o site e como podemos
                      melhorar sua experiência. Esses cookies podem rastrear
                      itens como quanto tempo você gasta no site e as páginas
                      visitadas, para que possamos continuar produzindo conteúdo
                      atraente.
                    </li>
                  </ul>
                  Para mais informações sobre cookies do Google Analytics,
                  consulte a página oficial do Google Analytics.
                  <ul className="terms-list">
                    <li>
                      As análises de terceiros são usadas para rastrear e medir
                      o uso deste site, para que possamos continuar produzindo
                      conteúdo atrativo. Esses cookies podem rastrear itens como
                      o tempo que você passa no site ou as páginas visitadas, o
                      que nos ajuda a entender como podemos melhorar o site para
                      você.
                    </li>
                    <li>
                      Periodicamente, testamos novos recursos e fazemos
                      alterações subtis na maneira como o site se apresenta.
                      Quando ainda estamos testando novos recursos, esses
                      cookies podem ser usados ​​para garantir que você receba
                      uma experiência consistente enquanto estiver no site,
                      enquanto entendemos quais otimizações os nossos usuários
                      mais apreciam.
                    </li>
                    <li>
                      À medida que vendemos produtos, é importante entendermos
                      as estatísticas sobre quantos visitantes de nosso site
                      realmente compram e, portanto, esse é o tipo de dados que
                      esses cookies rastrearão. Isso é importante para você,
                      pois significa que podemos fazer previsões de negócios com
                      precisão que nos permitem analizar nossos custos de
                      publicidade e produtos para garantir o melhor preço
                      possível.
                    </li>
                  </ul>
                </span>

                <h3 className="subtitle-terms">Compromisso do Usuário</h3>
                <br></br>
                <span>
                  O usuário se compromete a fazer uso adequado dos conteúdos e
                  da informação que o RecruIT oferece no site e com caráter
                  enunciativo, mas não limitativo:
                  <ul className="terms-list">
                    <li>
                      Não se envolver em atividades que sejam ilegais ou
                      contrárias à boa fé a à ordem pública;
                    </li>
                    <li>
                      Não divulgar conteúdo ou propaganda de natureza racista,
                      xenofóbica, casas de apostas legais, pornografia ilegal,
                      de apologia ao terrorismo ou contra os direitos humanos;
                    </li>
                    <li>
                      Não causar danos aos sistemas físicos (hardwares) e
                      lógicos (softwares) do RecruIT, de seus fornecedores ou
                      terceiros, para introduzir ou disseminar vírus
                      informáticos ou quaisquer outros sistemas de hardware ou
                      software que sejam capazes de causar danos anteriormente
                      mencionados.
                    </li>
                  </ul>
                </span>

                <h3 className="subtitle-terms">Mais Informações</h3>
                <br></br>
                <span>
                  Esperamos que esteja esclarecido e, como mencionado
                  anteriormente, se houver algo que você não tem certeza se
                  precisa ou não, geralmente é mais seguro deixar os cookies
                  ativados, caso interaja com um dos recursos que você usa em
                  nosso site.
                </span>
              </Card.Text>
            </Card>
          </Container>
        </Card>
      </Container>
    </Main>
  );
};

export default Terms;
